package com.ahcloud.edas.admin.biz.application.helper;

import cn.hutool.core.util.RandomUtil;
import com.ahcloud.edas.admin.biz.infrastructure.constant.enums.SexEnum;
import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.util.CollectionUtils;
import com.ahcloud.edas.common.util.SnowIdUtils;
import com.ahcloud.edas.uaa.client.UserUtils;
import com.ahcloud.edas.uaa.starter.core.constant.enums.UserStatusEnum;
import com.ahcloud.edas.uaa.starter.domain.authority.DefaultAuthority;
import com.ahcloud.edas.uaa.starter.domain.EdasUser;
import com.ahcloud.edas.admin.biz.domain.user.form.SysUserAddForm;
import com.ahcloud.edas.admin.biz.domain.user.form.SysUserInfoUpdateForm;
import com.ahcloud.edas.admin.biz.domain.user.form.SysUserUpdateForm;
import com.ahcloud.edas.admin.biz.domain.user.vo.BaseUserInfoVo;
import com.ahcloud.edas.admin.biz.domain.user.vo.GetUserInfoVo;
import com.ahcloud.edas.admin.biz.domain.user.vo.SysUserVo;
import com.ahcloud.edas.admin.biz.infrastructure.constant.AdminConstants;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUser;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserApi;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserMenu;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserRole;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-27 16:59
 **/
@Component
public class SysUserHelper {
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 构建系统用户实体
     *
     * @param form
     * @return
     */
    public SysUser buildSysUserEntity(SysUserAddForm form) {
        SysUser sysUser = this.convertToEntity(form);
        sysUser.setUserId(SnowIdUtils.randomLongId());
        sysUser.setAccount(form.getPhone());
        sysUser.setPassword(this.encodePassword(AdminConstants.DEFAULT_PASSWORD));
        sysUser.setNickName(StringUtils.isEmpty(sysUser.getNickName()) ? sysUser.getNickName() : generateNickName());
        sysUser.setStatus(UserStatusEnum.NORMAL.getStatus());
        sysUser.setName(form.getName());
        sysUser.setAvatar("https://i.gtimg.cn/club/item/face/img/3/15643_100.gif");
        sysUser.setCreator(UserUtils.getUserNameBySession());
        sysUser.setModifier(UserUtils.getUserNameBySession());
        return sysUser;
    }

    /**
     * 生成随机昵称
     *
     * @return
     */
    public String generateNickName() {
        return RandomUtil.randomString(8);
    }

    /**
     * 数据转换
     *
     * @param form
     * @return
     */
    public SysUser convertToEntity(SysUserAddForm form) {
        return SysUserConvert.INSTANCE.convert(form);
    }

    /**
     * 数据转换
     *
     * @param sysUser
     * @return
     */
    public SysUserVo convertToVo(SysUser sysUser) {
        return SysUserConvert.INSTANCE.convert(sysUser);
    }

    public PageResult<SysUserVo> convert2PageResult(PageInfo<SysUser> pageInfo) {
        PageResult<SysUserVo> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setPageNum(pageInfo.getPageNum());
        result.setRows(SysUserConvert.INSTANCE.convert(pageInfo.getList()));
        result.setPageSize(pageInfo.getPageSize());
        result.setPages(pageInfo.getPages());
        return result;
    }

    /**
     * 加密密码
     *
     * @param oldPassword
     * @return
     */
    public String encodePassword(String oldPassword) {
        return passwordEncoder.encode(oldPassword);
    }

    /**
     * 构建用户角色关联实体
     * @param userId
     * @param roleCodeList
     * @return
     */
    public List<SysUserRole> buildSysUserRoleEntityList(Long userId, List<String> roleCodeList) {
        List<SysUserRole> sysUserRoleList = Lists.newArrayList();
        String userNameBySession = UserUtils.getUserNameBySession();
        for (String roleCode : roleCodeList) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleCode(roleCode);
            sysUserRole.setCreator(userNameBySession);
            sysUserRole.setModifier(userNameBySession);
            sysUserRoleList.add(sysUserRole);
        }
        return sysUserRoleList;
    }


    public List<SysUserMenu> getSysUserMenuEntityList(Long userId, List<Long> menuIdList) {
        List<SysUserMenu> sysUserMenuList = Lists.newArrayList();
        String userNameBySession = UserUtils.getUserNameBySession();
        for (Long menuId : menuIdList) {
            SysUserMenu sysUserMenu = new SysUserMenu();
            sysUserMenu.setUserId(userId);
            sysUserMenu.setMenuId(menuId);
            sysUserMenu.setCreator(userNameBySession);
            sysUserMenu.setModifier(userNameBySession);
            sysUserMenuList.add(sysUserMenu);
        }
        return sysUserMenuList;
    }

    public List<SysUserApi> getSysUserApiEntityList(Long userId, Collection<String> apiCodeList) {
        Set<String> distinctApiCodeList = Sets.newHashSet(apiCodeList);
        List<SysUserApi> sysUserApiList = Lists.newArrayList();
        String userNameBySession = UserUtils.getUserNameBySession();
        for (String apiCode : distinctApiCodeList) {
            SysUserApi sysUserApi = new SysUserApi();
            sysUserApi.setUserId(userId);
            sysUserApi.setApiCode(apiCode);
            sysUserApi.setCreator(userNameBySession);
            sysUserApi.setModifier(userNameBySession);
            sysUserApiList.add(sysUserApi);
        }
        return sysUserApiList;
    }

    /**
     * 获取当前登录人
     *
     * @param edasUser
     * @return
     */
    public GetUserInfoVo convertToGetUserInfo(SysUser sysUser, EdasUser edasUser) {
        return GetUserInfoVo.builder()
                .userName(sysUser.getName())
                .avatar(sysUser.getAvatar())
                .permissions(CollectionUtils.convertSet(edasUser.getAuthorityInfo().getAuthorities(), DefaultAuthority::getAuthority))
                .build();
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysUser convertToEntity(SysUserUpdateForm form) {
        SysUser sysUser = SysUserConvert.INSTANCE.convert(form);
        sysUser.setModifier(UserUtils.getUserNameBySession());
        return sysUser;
    }

    /**
     * 数据转换
     * @param sysUser
     * @param deptName
     * @return
     */
    public BaseUserInfoVo convertToBaseUserInfoVo(SysUser sysUser, String deptName) {
        return SysUserConvert.INSTANCE.convertToBaseUserInfoVo(sysUser, deptName);
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysUser convertToInfoUpdateForm(SysUserInfoUpdateForm form) {
        return SysUserConvert.INSTANCE.convertToInfoUpdateForm(form);
    }

    @Mapper
    public interface SysUserConvert {
        SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        SysUser convert(SysUserAddForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        SysUser convert(SysUserUpdateForm form);

        /**
         * 数据转换
         *
         * @param sysUser
         * @return
         */
        @Mappings({
                @Mapping(target = "sexName", expression = "java(com.ahcloud.edas.admin.biz.infrastructure.constant.enums.SexEnum.getByType(sysUser.getSex()).getDesc())"),
                @Mapping(target = "statusName", expression = "java(com.ahcloud.edas.uaa.starter.core.constant.enums.UserStatusEnum.valueOf(sysUser.getStatus()).getDesc())")
        })
        SysUserVo convert(SysUser sysUser);

        /**
         * 数据转换
         *
         * @param sysUsers
         * @return
         */
        List<SysUserVo> convert(List<SysUser> sysUsers);

        /**
         * 数据转换
         * @param sysUser
         * @param deptName
         * @return
         */
        @Mappings({
                @Mapping(target = "sexName", expression = "java(com.ahcloud.edas.admin.biz.infrastructure.constant.enums.SexEnum.getByType(sysUser.getSex()).getDesc())"),
                @Mapping(target = "deptName", source = "deptName"),
        })
        BaseUserInfoVo convertToBaseUserInfoVo(SysUser sysUser, String deptName);

        /**
         * 数据转换
         * @param form
         * @return
         */
        SysUser convertToInfoUpdateForm(SysUserInfoUpdateForm form);
    }
}
