package com.ahcloud.edas.admin.api.controller;

import com.ahcloud.edas.admin.biz.application.manager.SysUserManager;
import com.ahcloud.edas.admin.biz.domain.user.form.SysUserAddForm;
import com.ahcloud.edas.admin.biz.domain.user.form.SysUserApiAddForm;
import com.ahcloud.edas.admin.biz.domain.user.form.SysUserInfoUpdateForm;
import com.ahcloud.edas.admin.biz.domain.user.form.SysUserMenuAddForm;
import com.ahcloud.edas.admin.biz.domain.user.form.SysUserRoleAddForm;
import com.ahcloud.edas.admin.biz.domain.user.form.SysUserUpdateForm;
import com.ahcloud.edas.admin.biz.domain.user.query.SelectUserApiQuery;
import com.ahcloud.edas.admin.biz.domain.user.query.SysUserQuery;
import com.ahcloud.edas.admin.biz.domain.user.vo.GetUserInfoVo;
import com.ahcloud.edas.admin.biz.domain.user.vo.SelectSysUserVo;
import com.ahcloud.edas.admin.biz.domain.user.vo.SelectUserApiVo;
import com.ahcloud.edas.admin.biz.domain.user.vo.SelectUserRoleVo;
import com.ahcloud.edas.admin.biz.domain.user.vo.SysUserPersonalInfoVo;
import com.ahcloud.edas.admin.biz.domain.user.vo.SysUserVo;
import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-27 14:19
 **/
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Resource
    private SysUserManager sysUserManager;

    /**
     * 添加新用户
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@Valid @RequestBody SysUserAddForm form) {
        sysUserManager.addSysUser(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 更新用户
     * @param form
     * @return
     */
    
    @PostMapping("/update")
    public ResponseResult<Void> update(@Valid @RequestBody SysUserUpdateForm form) {
        sysUserManager.updateSysUser(form);
        return ResponseResult.ofSuccess();
    }


    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> delete(@PathVariable(value = "id") Long id) {
        sysUserManager.deleteSysUserById(id);
        return ResponseResult.ofSuccess();
    }

    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<SysUserVo> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ofSuccess(sysUserManager.findSysUserById(id));
    }

    /**
     * 分页查询角色列表
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<SysUserVo>> page(SysUserQuery query) {
        return ResponseResult.ofSuccess(sysUserManager.pageSysUsers(query));
    }

    /**
     * 设置用户角色
     * @param form
     * @return
     */
    
    @PostMapping("/setSysUserRole")
    public ResponseResult<Void> setSysUserRole(@Valid @RequestBody SysUserRoleAddForm form) {
        sysUserManager.setSysUserRole(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 设置菜单
     * @param form
     * @return
     */
    
    @PostMapping("/setSysMenuForUser")
    public ResponseResult<Void> setSysMenuForUser(@Valid @RequestBody SysUserMenuAddForm form) {
        sysUserManager.setSysMenuForUser(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 设置接口权限
     * @param form
     * @return
     */
    
    @PostMapping("/setSysApiForUser")
    public ResponseResult<Void> setSysApiForUser(@Valid @RequestBody SysUserApiAddForm form) {
        sysUserManager.setSysApiForUser(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 取消用户接口权限
     * @param form
     * @return
     */
    
    @PostMapping("/cancelSysApiForUser")
    public ResponseResult<Void> cancelSysApiForUser(@Valid @RequestBody SysUserApiAddForm form) {
        sysUserManager.cancelSysApiForUser(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/getUserInfoVo")
    public ResponseResult<GetUserInfoVo> getUserInfoVo() {
        return ResponseResult.ofSuccess(sysUserManager.getCurrentUserInfo());
    }

    /**
     * 根据用户id获取用户的角色信息
     * @param userId
     * @return
     */
    @GetMapping("/listSelectUserRole/{userId}")
    public ResponseResult<SelectUserRoleVo> listSelectUserRoleByUserId(@PathVariable("userId") Long userId) {
        return ResponseResult.ofSuccess(sysUserManager.listSelectUserRoleByUserId(userId));
    }

    /**
     * 根据用户id分页获取用户的角色信息
     * @param query
     * @return
     */
    @GetMapping("/pageSelectUserApi")
    public ResponseResult<PageResult<SelectUserApiVo.ApiInfoVo>> pageSelectUserApi(SelectUserApiQuery query) {
        return ResponseResult.ofSuccess(sysUserManager.pageSelectUserApi(query));
    }

    /**
     * 获取用户个人信息
     *
     * @return
     */
    @GetMapping("/findUserPersonalInfo")
    public ResponseResult<SysUserPersonalInfoVo> findUserPersonalInfoVo() {
        return ResponseResult.ofSuccess(sysUserManager.findSysUserPersonalInfoVo());
    }

    /**
     * 更新个人中心基本信息
     * @param form
     * @return
     */
    @PostMapping("/updatePersonalBaseUserInfo")
    public ResponseResult<Void> updatePersonalBaseUserInfo(@Valid @RequestBody SysUserInfoUpdateForm form) {
        sysUserManager.updatePersonalBaseUserInfo(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 获取系统用户选择列表
     * @return
     */
    @GetMapping("/listSelectSysUserVo")
    public ResponseResult<List<SelectSysUserVo>> listSelectSysUserVo() {
        return ResponseResult.ofSuccess(sysUserManager.listSelectSysUserVo());
    }

}
