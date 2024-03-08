package com.ahcloud.edas.admin.biz.application.manager;

import com.ahcloud.edas.admin.biz.application.service.SysAppParkService;
import com.ahcloud.edas.admin.biz.application.service.SysUserAppScopeService;
import com.ahcloud.edas.admin.biz.domain.app.form.AppAddForm;
import com.ahcloud.edas.admin.biz.domain.app.form.AppScopeForm;
import com.ahcloud.edas.admin.biz.domain.app.form.CancelScopeForm;
import com.ahcloud.edas.admin.biz.domain.app.query.AppBaseQuery;
import com.ahcloud.edas.admin.biz.domain.app.vo.AppBaseVO;
import com.ahcloud.edas.admin.biz.domain.app.vo.AppSelectVO;
import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.ErrorCodeEnum;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysAppPark;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserAppScope;
import com.ahcloud.edas.admin.biz.infrastructure.util.PageUtils;
import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.enums.AppTypeEnum;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.common.util.SnowIdUtils;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 15:45
 **/
@Slf4j
@Component
public class ApplicationManager {
    @Resource
    private SysAppParkService sysAppParkService;
    @Resource
    private SysUserAppScopeService sysUserAppScopeService;

    /**
     * 添加app应用
     * @param form
     */
    public void addApp(AppAddForm form) {
        List<String> envList = form.getEnvList();
        String userNameBySession = UserUtils.getUserNameBySession();
        List<SysAppPark> appParkList = envList.stream().map(env -> {
            SysAppPark sysAppPark = new SysAppPark();
            sysAppPark.setAppCode(form.getAppCode());
            sysAppPark.setAppName(form.getAppName());
            sysAppPark.setAppId(SnowIdUtils.randomLongId());
            sysAppPark.setEnv(env);
            sysAppPark.setType(form.getType());
            sysAppPark.setAppDesc(form.getAppDesc());
            sysAppPark.setCreator(userNameBySession);
            sysAppPark.setModifier(userNameBySession);
            return sysAppPark;
        }).collect(Collectors.toList());
        sysAppParkService.saveBatch(appParkList);
    }

    /**
     * 删除app
     * @param id
     */
    public void deleteAppById(Long id) {
        SysAppPark sysAppPark = sysAppParkService.getOne(
                new QueryWrapper<SysAppPark>().lambda()
                        .eq(SysAppPark::getId, id)
                        .eq(SysAppPark::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(sysAppPark)) {
            throw new BizException(ErrorCodeEnum.DATA_NOT_EXISTED);
        }
        SysAppPark deleteSysAppPark = new SysAppPark();
        deleteSysAppPark.setDeleted(id);
        deleteSysAppPark.setModifier(UserUtils.getUserNameBySession());
        deleteSysAppPark.setVersion(sysAppPark.getVersion());
        boolean delete = sysAppParkService.update(
                deleteSysAppPark,
                new UpdateWrapper<SysAppPark>().lambda()
                        .eq(SysAppPark::getId, id)
                        .eq(SysAppPark::getVersion, sysAppPark.getVersion())
        );
        if (!delete) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
    }

    /**
     * 分页查询appList
     * @param query
     * @return
     */
    public PageResult<AppBaseVO> pageAppBaseList(AppBaseQuery query) {
        PageInfo<SysAppPark> pageInfo = PageHelper.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> sysAppParkService.list(
                                new QueryWrapper<SysAppPark>().lambda()
                                        .eq(
                                                StringUtils.isNotBlank(query.getAppCode()),
                                                SysAppPark::getAppCode,
                                                query.getAppCode()
                                        )
                                        .like(
                                                StringUtils.isNotBlank(query.getAppName()),
                                                SysAppPark::getAppName,
                                                query.getAppName()
                                        )
                                        .eq(
                                                StringUtils.isNotBlank(query.getEnv()),
                                                SysAppPark::getEnv,
                                                query.getEnv()
                                        )
                        )
                );
        return getAppBaseVOPageResult(pageInfo);
    }

    /**
     * 分配app权限
     * @param form
     */
    public void assignAppScope(AppScopeForm form) {
        List<Long> appIdList = form.getAppIdList();
        int row = sysUserAppScopeService.delete(
                new QueryWrapper<SysUserAppScope>().lambda()
                        .eq(SysUserAppScope::getUserId, form.getUserId())
                        .in(SysUserAppScope::getAppId, appIdList)
                        .eq(SysUserAppScope::getDeleted, DeletedEnum.NO.value)
        );
        log.warn("分配app权限[assignAppScope] 删除已有数据，row={}", row);

        List<SysAppPark> sysAppParkList = sysAppParkService.list(
                new QueryWrapper<SysAppPark>().lambda()
                        .in(SysAppPark::getAppId, appIdList)
                        .eq(SysAppPark::getDeleted, DeletedEnum.NO.value)
        );
        Map<Long, SysAppPark> appParkMap = CollectionUtils.convertMap(sysAppParkList, SysAppPark::getAppId, Function.identity(), (k1, k2) -> k1);
        if (CollectionUtils.isEmpty(appParkMap)) {
            throw new BizException(ErrorCodeEnum.DATA_NOT_EXISTED);
        }
        String userNameBySession = UserUtils.getUserNameBySession();
        List<SysUserAppScope> sysUserAppScopeList = Lists.newArrayList();
        for (Long appId : appIdList) {
            SysAppPark sysAppPark = appParkMap.get(appId);
            if (Objects.isNull(sysAppPark)) {
                continue;
            }
            SysUserAppScope sysUserAppScope = new SysUserAppScope();
            sysUserAppScope.setUserId(form.getUserId());
            sysUserAppScope.setAppCode(sysAppPark.getAppCode());
            sysUserAppScope.setAppId(sysAppPark.getAppId());
            sysUserAppScope.setEnv(sysAppPark.getEnv());
            sysUserAppScope.setCreator(userNameBySession);
            sysUserAppScope.setModifier(userNameBySession);
            sysUserAppScopeList.add(sysUserAppScope);
        }
        sysUserAppScopeService.saveBatch(sysUserAppScopeList);
    }

    /**
     * 取消分配app权限
     * @param form
     */
    public void cancelAssign(AppScopeForm form) {
        int row = sysUserAppScopeService.delete(
                new QueryWrapper<SysUserAppScope>().lambda()
                        .eq(SysUserAppScope::getUserId, form.getUserId())
                        .in(SysUserAppScope::getAppId, form.getAppIdList())
                        .eq(SysUserAppScope::getDeleted, DeletedEnum.NO.value)
        );
        log.warn("分配app权限[assignAppScope] 取消分配app权限，row={}", row);
    }

    /**
     * 删除用户权限
     * @param id
     */
    public void deletedUserAppScopeById(Long id) {
        SysUserAppScope existedUserAppScope = sysUserAppScopeService.getOne(
                new QueryWrapper<SysUserAppScope>().lambda()
                        .eq(SysUserAppScope::getId, id)
                        .eq(SysUserAppScope::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existedUserAppScope)) {
            throw new BizException(ErrorCodeEnum.DATA_NOT_EXISTED);
        }
        SysUserAppScope deletedUserAppScope = new SysUserAppScope();
        deletedUserAppScope.setDeleted(id);
        deletedUserAppScope.setModifier(UserUtils.getUserNameBySession());
        deletedUserAppScope.setVersion(existedUserAppScope.getVersion());
        boolean deleted = sysUserAppScopeService.update(
                deletedUserAppScope,
                new UpdateWrapper<SysUserAppScope>().lambda()
                        .eq(SysUserAppScope::getId, id)
                        .eq(SysUserAppScope::getVersion, existedUserAppScope.getVersion())
        );
        if (!deleted) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
    }

    /**
     * 查询用户app权限集合
     * @param query
     * @return
     */
    public PageResult<AppBaseVO> pageUserAppScopeList(AppBaseQuery query) {
        PageInfo<SysAppPark> pageInfo = PageHelper.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> sysUserAppScopeService.listByQuery(query)
                );
        return getAppBaseVOPageResult(pageInfo);
    }

    /**
     * 获取我的app选择列表
     * @param query
     * @return
     */
    public List<AppSelectVO> getMyAppSelectList(AppBaseQuery query) {
        query.setUserId(UserUtils.getUserIdBySession());
        return getAppListByQuery(query);
    }

    private static PageResult<AppBaseVO> getAppBaseVOPageResult(PageInfo<SysAppPark> pageInfo) {
        List<SysAppPark> list = pageInfo.getList();
        List<AppBaseVO> appBaseVOList = list.stream().map(
                sysAppPark -> {
                    AppBaseVO appBaseVO = new AppBaseVO();
                    appBaseVO.setId(sysAppPark.getId());
                    appBaseVO.setAppId(sysAppPark.getAppId());
                    appBaseVO.setAppCode(sysAppPark.getAppCode());
                    appBaseVO.setAppName(sysAppPark.getAppName());
                    appBaseVO.setEnv(sysAppPark.getEnv());
                    appBaseVO.setType(sysAppPark.getType());
                    appBaseVO.setTypeName(AppTypeEnum.getByType(sysAppPark.getType()).getDesc());
                    return appBaseVO;
                }
        ).collect(Collectors.toList());
        return PageUtils.pageInfoConvertToPageResult(pageInfo, appBaseVOList);
    }

    /**
     * 根据appId获取应用详情
     * @param appId
     * @return
     */
    public AppBaseVO findAppByAppId(Long appId) {
        SysAppPark sysAppPark = sysAppParkService.getOne(
                new QueryWrapper<SysAppPark>().lambda()
                        .eq(SysAppPark::getAppId, appId)
                        .eq(SysAppPark::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(sysAppPark)) {
            throw new BizException(ErrorCodeEnum.DATA_NOT_EXISTED);
        }
        AppBaseVO appBaseVO = new AppBaseVO();
        appBaseVO.setId(sysAppPark.getId());
        appBaseVO.setAppId(sysAppPark.getAppId());
        appBaseVO.setAppCode(sysAppPark.getAppCode());
        appBaseVO.setAppName(sysAppPark.getAppName());
        appBaseVO.setEnv(sysAppPark.getEnv());
        appBaseVO.setType(sysAppPark.getType());
        appBaseVO.setTypeName(AppTypeEnum.getByType(sysAppPark.getType()).getDesc());
        return appBaseVO;
    }

    /**
     * 获取app选择列表
     * @param userId
     * @return
     */
    public List<AppSelectVO> getAppSelectListByUserId(Long userId, String env) {
        AppBaseQuery query = new AppBaseQuery();
        query.setEnv(env);
        query.setUserId(userId);
        return getAppListByQuery(query);
    }

    private List<AppSelectVO> getAppListByQuery(AppBaseQuery query) {
        List<SysAppPark> myAppList = sysUserAppScopeService.listByQuery(query);
        return myAppList.stream()
                .map(
                        sysAppPark -> {
                            AppSelectVO appSelectVO = new AppSelectVO();
                            appSelectVO.setAppId(sysAppPark.getAppId());
                            appSelectVO.setAppCodeAndEnv(String.format(sysAppPark.getAppCode() + "【%s】", sysAppPark.getEnv()));
                            return appSelectVO;
                        }
                ).collect(Collectors.toList());
    }
}
