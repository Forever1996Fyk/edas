package com.ahcloud.edas.admin.biz.application.manager;

import com.ahcloud.edas.admin.biz.application.helper.SysRoleHelper;
import com.ahcloud.edas.admin.biz.application.helper.SysUserHelper;
import com.ahcloud.edas.admin.biz.application.helper.UserAuthorityHelper;
import com.ahcloud.edas.admin.biz.application.service.SysApiService;
import com.ahcloud.edas.admin.biz.application.service.SysRoleMenuService;
import com.ahcloud.edas.admin.biz.application.service.SysRoleService;
import com.ahcloud.edas.admin.biz.application.service.ext.SysUserApiExtService;
import com.ahcloud.edas.admin.biz.application.service.ext.SysUserExtService;
import com.ahcloud.edas.admin.biz.application.service.ext.SysUserMenuExtService;
import com.ahcloud.edas.admin.biz.application.service.ext.SysUserRoleExtService;
import com.ahcloud.edas.admin.biz.domain.app.form.AppScopeForm;
import com.ahcloud.edas.admin.biz.domain.menu.vo.RouterPermissionVO;
import com.ahcloud.edas.admin.biz.domain.menu.vo.RouterVo;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.ahcloud.edas.uaa.starter.core.constant.enums.UserStatusEnum;
import com.ahcloud.edas.uaa.starter.domain.EdasUser;
import com.ahcloud.edas.admin.biz.domain.user.dto.SelectSysUserDTO;
import com.ahcloud.edas.admin.biz.domain.user.form.SysUserAddForm;
import com.ahcloud.edas.admin.biz.domain.user.form.SysUserApiAddForm;
import com.ahcloud.edas.admin.biz.domain.user.form.SysUserInfoUpdateForm;
import com.ahcloud.edas.admin.biz.domain.user.form.SysUserMenuAddForm;
import com.ahcloud.edas.admin.biz.domain.user.form.SysUserRoleAddForm;
import com.ahcloud.edas.admin.biz.domain.user.form.SysUserUpdateForm;
import com.ahcloud.edas.admin.biz.domain.user.query.SelectUserApiQuery;
import com.ahcloud.edas.admin.biz.domain.user.query.SysUserExportQuery;
import com.ahcloud.edas.admin.biz.domain.user.query.SysUserQuery;
import com.ahcloud.edas.admin.biz.domain.user.vo.BaseUserInfoVo;
import com.ahcloud.edas.admin.biz.domain.user.vo.GetUserInfoVo;
import com.ahcloud.edas.admin.biz.domain.user.vo.SelectSysUserVo;
import com.ahcloud.edas.admin.biz.domain.user.vo.SelectUserApiVo;
import com.ahcloud.edas.admin.biz.domain.user.vo.SelectUserRoleVo;
import com.ahcloud.edas.admin.biz.domain.user.vo.SysUserPersonalInfoVo;
import com.ahcloud.edas.admin.biz.domain.user.vo.SysUserVo;
import com.ahcloud.edas.admin.biz.infrastructure.constant.AdminConstants;
import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.ErrorCodeEnum;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysApi;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysRole;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysRoleMenu;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUser;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserApi;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserMenu;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserRole;
import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.enums.DeletedEnum;
import com.ahcloud.edas.common.exception.BizException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description: 系统用户管理器
 * @author: YuKai Fan
 * @create: 2021-12-27 16:59
 **/
@Slf4j
@Component
public class SysUserManager {
    @Resource
    private SysApiService sysApiService;
    @Resource
    private AccessManager accessManager;
    @Resource
    private SysUserHelper sysUserHelper;
    @Resource
    private SysRoleHelper sysRoleHelper;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysMenuManager sysMenuManager;
    @Resource
    private SysUserExtService sysUserExtService;
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysUserIntroManager sysUserIntroManager;
    @Resource
    private SysUserApiExtService sysUserApiExtService;
    @Resource
    private SysUserMenuExtService sysUserMenuExtService;
    @Resource
    private SysUserRoleExtService sysUserRoleExtService;

    /**
     * 添加新用户
     *
     *  @param form
     */
    public void addSysUser(SysUserAddForm form) {

        /*
        判断用户手机号是否已存在
         */
        List<SysUser> sysUsers = sysUserExtService.list(
                new QueryWrapper<SysUser>().lambda()
                        .eq(SysUser::getPhone, form.getPhone())
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
        if (!CollectionUtils.isEmpty(sysUsers)) {
            throw new BizException(ErrorCodeEnum.USER_PHONE_IS_EXISTED, form.getPhone());
        }
        /*
        数据转换
         */
        SysUser sysUser = sysUserHelper.buildSysUserEntity(form);

        /*
        保存用户信息
         */
        sysUserExtService.save(sysUser);
    }

    /**
     * 逻辑删除用户
     *
     * @param id
     */
    public void deleteSysUserById(Long id) {
        /*
        获取当前用户
         */
        SysUser sysUser = sysUserExtService.getById(id);
        if (Objects.isNull(sysUser)) {
            throw new BizException(ErrorCodeEnum.USER_NOT_EXIST);
        }
        /*
        更新用户删除标识
         */
        sysUser.setDeleted(id);
        sysUser.setModifier(String.valueOf(UserUtils.getBaseUserInfo().getUserId()));
        sysUserExtService.updateById(sysUser);
    }

    /**
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    public SysUserVo findSysUserById(Long id) {
        SysUser sysUser = sysUserExtService.getById(id);
        if (Objects.isNull(sysUser)) {
            throw new BizException(ErrorCodeEnum.USER_NOT_EXIST);
        }
        return sysUserHelper.convertToVo(sysUser);
    }

    /**
     * 分页查询用户信息
     *
     * @param query
     * @return
     */
    public PageResult<SysUserVo> pageSysUsers(SysUserQuery query) {
        PageInfo<SysUser> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> sysUserExtService.list(
                                new QueryWrapper<SysUser>().lambda()
                                        .like(
                                                StringUtils.isNotBlank(query.getNickName()),
                                                SysUser::getNickName, query.getNickName())
                                        .eq(
                                                StringUtils.isNotBlank(query.getPhone()),
                                                SysUser::getPhone, query.getPhone())
                                        .like(
                                                StringUtils.isNotBlank(query.getEmail()),
                                                SysUser::getEmail, query.getEmail())
                                        .eq(
                                                StringUtils.isNotBlank(query.getDeptCode()),
                                                SysUser::getDeptCode, query.getDeptCode()
                                        )
                                        .eq(
                                                SysUser::getDeleted,
                                                DeletedEnum.NO.value)
                        ));
        return sysUserHelper.convert2PageResult(pageInfo);
    }

    /**
     * 设置用户角色
     * <p>
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void setSysUserRole(SysUserRoleAddForm form) {
        /*
        先删除原数据, 在更新用户角色
         */
        int countDeleted = sysUserRoleExtService.delete(
                new QueryWrapper<SysUserRole>().lambda()
                        .eq(SysUserRole::getUserId, form.getUserId())
        );

        if (CollectionUtils.isEmpty(form.getRoleCodeList())) {
            log.warn("SysUserManager[setSysUserRole] delete SysUserRole allData, userId is {}, oldUserRoleCount is {}", form.getUserId(), countDeleted);
            return;
        }

        List<SysUserRole> sysUserRoleList = sysUserHelper.buildSysUserRoleEntityList(form.getUserId(), form.getRoleCodeList());
        sysUserRoleExtService.saveBatch(sysUserRoleList);

        // 异步处理用户权限更新
        ThreadPoolManager.updateUserThreadPool.execute(() -> accessManager.updateUserCache(form.getUserId()));
    }

    /**
     * 设置用户菜单
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void setSysMenuForUser(SysUserMenuAddForm form) {
        Long userId = form.getUserId();
        List<Long> menuIdList = form.getMenuIdList();
        /*
        先删除原有的用户菜单列表, 再重新添加
         */
        sysUserMenuExtService.delete(
                new QueryWrapper<SysUserMenu>().lambda()
                        .eq(SysUserMenu::getUserId, userId)
                        .eq(SysUserMenu::getDeleted, DeletedEnum.NO.value)
        );

        if (CollectionUtils.isEmpty(menuIdList)) {
            log.warn("SysUserManager[setSysMenuForUser] delete SysUserMenu allData, userId={}", userId);
            return;
        }
        List<SysUserMenu> sysUserMenuList = sysUserHelper.getSysUserMenuEntityList(userId, menuIdList);
        sysUserMenuExtService.saveBatch(sysUserMenuList);
        // 异步处理用户权限更新
        ThreadPoolManager.updateUserThreadPool.execute(() -> accessManager.updateUserCache(form.getUserId()));
    }

    /**
     * 设置用户api
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void setSysApiForUser(SysUserApiAddForm form) {
        Long userId = form.getUserId();
        List<String> apiCodeList = form.getApiCodeList();
        /*
        先删除原有的用户权限列表, 再重新添加
         */
        sysUserApiExtService.delete(
                new QueryWrapper<SysUserApi>().lambda()
                        .eq(SysUserApi::getUserId, userId)
                        .in(SysUserApi::getApiCode, apiCodeList)
                        .eq(SysUserApi::getDeleted, DeletedEnum.NO.value)
        );
        if (CollectionUtils.isEmpty(apiCodeList)) {
            return;
        }
        // 去重apiCode
        List<SysUserApi> sysUserApiList = sysUserHelper.getSysUserApiEntityList(userId, apiCodeList);
        sysUserApiExtService.saveBatch(sysUserApiList);
        // 异步处理用户权限更新
        ThreadPoolManager.updateUserThreadPool.execute(() -> accessManager.updateUserCache(userId));
    }

    /**
     * 取消用户api
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelSysApiForUser(SysUserApiAddForm form) {
        List<String> apiCodeList = form.getApiCodeList();
        if (CollectionUtils.isEmpty(apiCodeList)) {
            return;
        }
        List<SysUserApi> sysUserApiList = sysUserApiExtService.list(
                new QueryWrapper<SysUserApi>().lambda()
                        .eq(SysUserApi::getUserId, form.getUserId())
                        .in(SysUserApi::getApiCode, apiCodeList)
                        .eq(SysUserApi::getDeleted, DeletedEnum.NO.value)
        );

        String userNameBySession = UserUtils.getUserNameBySession();
        List<SysUserApi> cancelSysUserApiList = sysUserApiList.stream()
                .map(sysUserApi -> {
                    SysUserApi cancelSysUserApi = new SysUserApi();
                    cancelSysUserApi.setId(sysUserApi.getId());
                    cancelSysUserApi.setDeleted(sysUserApi.getId());
                    cancelSysUserApi.setModifier(userNameBySession);
                    return cancelSysUserApi;
                }).collect(Collectors.toList());
        sysUserApiExtService.updateBatchById(cancelSysUserApiList);
        // 异步处理用户权限更新
        ThreadPoolManager.updateUserThreadPool.execute(() -> accessManager.updateUserCache(form.getUserId()));
    }


    /**
     * 获取当前用户菜单路由
     *
     * @return
     */
    public RouterPermissionVO listRouterVoByUserId(Long userId) {
        if (UserAuthorityHelper.isAdmin(userId)) {
            return sysMenuManager.assembleMenuRouteByUser(null);
        }
        // 根据用户id获取菜单列表
        List<SysUserMenu> sysUserMenuList = sysUserMenuExtService.list(
                new QueryWrapper<SysUserMenu>().lambda()
                        .eq(SysUserMenu::getUserId, userId)
                        .eq(SysUserMenu::getDeleted, DeletedEnum.NO.value)
        );

        // 根据用户id获取角色列表
        List<SysUserRole> sysUserRoleList = sysUserRoleExtService.list(
                new QueryWrapper<SysUserRole>().lambda()
                        .select(SysUserRole::getRoleCode)
                        .eq(SysUserRole::getUserId, userId)
                        .eq(SysUserRole::getDeleted, DeletedEnum.NO.value)
        );

        // 根据角色编码获取菜单列表
        Set<String> roleSet = sysUserRoleList.stream()
                .map(SysUserRole::getRoleCode)
                .collect(Collectors.toSet());

        Set<Long> userMenuSet = sysUserMenuList.stream()
                .map(SysUserMenu::getMenuId)
                .collect(Collectors.toSet());

        if (CollectionUtils.isNotEmpty(roleSet)) {
            List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(
                    new QueryWrapper<SysRoleMenu>().lambda()
                            .in(SysRoleMenu::getRoleCode, roleSet)
                            .eq(SysRoleMenu::getDeleted, DeletedEnum.NO.value)
            );
            Set<Long> roleMenuSet = sysRoleMenuList.stream()
                    .map(SysRoleMenu::getMenuId)
                    .collect(Collectors.toSet());
            userMenuSet.addAll(roleMenuSet);
        }
        return sysMenuManager.assembleMenuRouteByUser(userMenuSet);
    }

    /**
     * 根据用户id集合获取系统用户
     *
     * @param userIds
     * @return
     */
    public List<SysUser> listSysUserByUserIds(Collection<Long> userIds) {
        return sysUserExtService.list(
                new QueryWrapper<SysUser>().lambda()
                        .in(SysUser::getUserId, userIds)
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
    }

    /**
     * 根据用户id获取当前部门负责人id
     *
     * @param proposerId
     * @return
     */
    public SysUser findLeaderL1ByUserId(Long proposerId) {
        SysUser sysUser = sysUserExtService.getOneByUserId(proposerId);
        String deptCode = sysUser.getDeptCode();
        // 根据部门编码获取部门负责人id
        Long deptLeaderId = 0L;
        return Objects.equals(proposerId, deptLeaderId) ? sysUser : sysUserExtService.getOneByUserId(deptLeaderId);
    }

    /**
     * 获取用户选择list
     *
     * @return
     */
    public List<SelectSysUserDTO> selectSysUserDTOList() {
        List<SysUser> sysUserList = sysUserExtService.list(
                new QueryWrapper<SysUser>().lambda()
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
                        .eq(SysUser::getStatus, UserStatusEnum.NORMAL.getStatus())
        );
        return sysUserList.stream()
                .map(sysUser -> SelectSysUserDTO.builder().code(String.valueOf(sysUser.getUserId())).name(sysUser.getNickName()).build())
                .collect(Collectors.toList());
    }

    /**
     * 流式查询用户导出列表
     *
     * @param query
     * @param resultHandler
     */
    public void listSysUserForExport(SysUserExportQuery query, ResultHandler<SysUser> resultHandler) {
        sysUserExtService.streamQueryForExport(query, resultHandler);
    }

    /**
     * 批量导入用户数据
     *
     * @param sysUserList
     */
    public void batchImportSysUser(List<SysUser> sysUserList) {
        sysUserExtService.saveBatch(sysUserList);
    }

    /**
     * 获取当前登录人信息
     *
     * @return
     */
    public GetUserInfoVo getCurrentUserInfo() {
        EdasUser edasUser = UserUtils.getEdasUser();
        if (Objects.isNull(edasUser)) {
            throw new BizException(ErrorCodeEnum.LOGIN_INVALID);
        }
        Long userIdBySession = UserUtils.getUserIdBySession();
        SysUser sysUser = sysUserExtService.getOneByUserId(userIdBySession);
        return sysUserHelper.convertToGetUserInfo(sysUser, edasUser);
    }

    /**
     * 更新用户
     *
     * @param form
     */
    public void updateSysUser(SysUserUpdateForm form) {
        SysUser existSysUser = sysUserExtService.getOne(
                new QueryWrapper<SysUser>().lambda()
                        .eq(SysUser::getId, form.getId())
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existSysUser)) {
            throw new BizException(ErrorCodeEnum.USER_NOT_EXIST);
        }
        SysUser updateSysUser = sysUserHelper.convertToEntity(form);
        sysUserExtService.updateById(updateSysUser);
    }

    /**
     * 根据用户id查询用户已分配和未分配的角色集合
     *
     * @param userId
     * @return
     */
    public SelectUserRoleVo listSelectUserRoleByUserId(Long userId) {
        if (Objects.isNull(userId) || Objects.equals(userId, AdminConstants.ZERO)) {
            throw new BizException(ErrorCodeEnum.PARAM_ILLEGAL);
        }
        List<SysRole> sysRoleList = sysRoleService.list(
                new QueryWrapper<SysRole>().lambda()
                        .eq(SysRole::getDeleted, DeletedEnum.NO.value)
        );
        List<SysUserRole> sysUserRoleList = sysUserRoleExtService.list(
                new QueryWrapper<SysUserRole>().lambda()
                        .eq(SysUserRole::getUserId, userId)
                        .eq(SysUserRole::getDeleted, DeletedEnum.NO.value)
        );
        List<String> sysUserRoleCodeList = sysUserRoleList.stream().map(SysUserRole::getRoleCode).collect(Collectors.toList());
        Set<SelectUserRoleVo.RoleInfoVo> allocatedRoleSet = Sets.newHashSet();

        List<SelectUserRoleVo.RoleInfoVo> roleInfoVoList = sysRoleHelper.convert2SelectUserRoleVoList(sysRoleList);
        roleInfoVoList.stream().filter(item -> sysUserRoleCodeList.contains(item.getRoleCode())).forEach(allocatedRoleSet::add);
        return SelectUserRoleVo.builder()
                .allocatedRoleSet(allocatedRoleSet)
                .allRoleSet(Sets.newHashSet(roleInfoVoList))
                .build();
    }

    /**
     * 根据用户id查询用户已分配接口集合
     *
     * @param query
     * @return
     */
    public PageResult<SelectUserApiVo.ApiInfoVo> pageSelectUserApi(SelectUserApiQuery query) {
        Long userId = query.getUserId();
        if (Objects.isNull(userId) || Objects.equals(userId, AdminConstants.ZERO)) {
            throw new BizException(ErrorCodeEnum.PARAM_ILLEGAL);
        }
        PageInfo<SysUserApi> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> sysUserApiExtService.list(
                                new QueryWrapper<SysUserApi>().lambda()
                                        .orderByDesc(SysUserApi::getCreatedTime)
                                        .eq(SysUserApi::getUserId, userId)
                                        .eq(StringUtils.isNotBlank(query.getApiCode()), SysUserApi::getApiCode, query.getApiCode())
                                        .eq(SysUserApi::getDeleted, DeletedEnum.NO.value)
                        )
                );
        PageResult<SelectUserApiVo.ApiInfoVo> pageResult = new PageResult<>();
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setTotal(pageInfo.getTotal());
        List<SysUserApi> sysUserApiList = pageInfo.getList();
        if (CollectionUtils.isNotEmpty(sysUserApiList)) {
            List<String> apiCodeList = sysUserApiList.stream().map(SysUserApi::getApiCode).collect(Collectors.toList());
            List<SysApi> sysApiList = sysApiService.list(
                    new QueryWrapper<SysApi>().lambda()
                            .select(SysApi::getApiName, SysApi::getApiCode, SysApi::getId)
                            .in(SysApi::getApiCode, apiCodeList)
                            .eq(SysApi::getDeleted, DeletedEnum.NO.value)
            );
            pageResult.setRows(
                    sysApiList.stream()
                            .map(item -> SelectUserApiVo.ApiInfoVo.builder().id(item.getId()).apiCode(item.getApiCode()).apiName(item.getApiName()).build())
                            .collect(Collectors.toList())
            );
        }
        return pageResult;
    }

    /**
     * 根据用户id获取个人信息
     *
     * @param userId
     * @return
     */
    public SysUserPersonalInfoVo findSysUserPersonalInfoVo() {
        Long userId = UserUtils.getUserIdBySession();
        return SysUserPersonalInfoVo.builder()
                .baseUserInfo(this.findBaseUserInfoVoByUserId(userId))
                .sysUserIntro(sysUserIntroManager.findSysUserIntroVoByUserId(userId))
                .build();
    }

    /**
     * 根据用户id查询用户基本信息
     *
     * @param userId
     * @return
     */
    public BaseUserInfoVo findBaseUserInfoVoByUserId(Long userId) {
        SysUser sysUser = sysUserExtService.getOneByUserId(userId);
        return sysUserHelper.convertToBaseUserInfoVo(sysUser, "");
    }

    /**
     * 修改用户昵称
     *
     * @param form
     */
    public void updatePersonalBaseUserInfo(SysUserInfoUpdateForm form) {
        Long userId = UserUtils.getUserIdBySession();
        if (Objects.equals(userId, AdminConstants.ZERO)) {
            throw new BizException(ErrorCodeEnum.LOGIN_INVALID);
        }
        SysUser sysUser = sysUserExtService.getOneByUserId(userId);
        if (Objects.isNull(sysUser)) {
            throw new BizException(ErrorCodeEnum.USER_NOT_EXIST);
        }
        SysUser updateSysUser = sysUserHelper.convertToInfoUpdateForm(form);
        updateSysUser.setId(sysUser.getId());
        boolean result = sysUserExtService.updateById(updateSysUser);
        if (!result) {
            throw new BizException(ErrorCodeEnum.USER_INFO_UPDATE_FAILED);
        }
    }

    /**
     * 获取用户选择列表
     *
     * 只能查询数据权限是用户本人的用户
     * @return
     */
    public List<SelectSysUserVo> listSelectSysUserVo() {
        List<SysUser> sysUserList = sysUserExtService.list(
                new QueryWrapper<SysUser>().lambda()
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
        return sysUserList.stream()
                .map(sysUser ->
                        SelectSysUserVo.builder()
                                .userId(sysUser.getUserId())
                                .name(sysUser.getName())
                                .build()
                )
                .collect(Collectors.toList());
    }
}
