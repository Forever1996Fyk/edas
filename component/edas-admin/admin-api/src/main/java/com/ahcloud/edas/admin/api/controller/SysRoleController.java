package com.ahcloud.edas.admin.api.controller;

import com.ahcloud.edas.admin.biz.application.manager.SysRoleManager;
import com.ahcloud.edas.admin.biz.domain.role.form.SysRoleAddForm;
import com.ahcloud.edas.admin.biz.domain.role.form.SysRoleApiAddForm;
import com.ahcloud.edas.admin.biz.domain.role.form.SysRoleMenuAddForm;
import com.ahcloud.edas.admin.biz.domain.role.form.SysRoleUpdateForm;
import com.ahcloud.edas.admin.biz.domain.role.query.SelectRoleApiQuery;
import com.ahcloud.edas.admin.biz.domain.role.query.SysRoleQuery;
import com.ahcloud.edas.admin.biz.domain.role.vo.SelectRoleApiVo;
import com.ahcloud.edas.admin.biz.domain.role.vo.SysRoleVO;
import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description: 权限角色controller
 * @author: YuKai Fan
 * @create: 2021-12-03 15:54
 **/
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Resource
    private SysRoleManager sysRoleManager;

    /**
     * 添加权限角色
     *
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@Valid @RequestBody SysRoleAddForm form) {
        sysRoleManager.addSysRole(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 编辑权限角色
     *
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@Valid @RequestBody SysRoleUpdateForm form) {
        sysRoleManager.updateSysRole(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 根据id删除角色
     *
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> delete(@PathVariable(value = "id") Long id) {
        sysRoleManager.deleteSysRole(id);
        return ResponseResult.ofSuccess();
    }

    /**
     * 根据id查询权限角色信息
     *
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<SysRoleVO> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ofSuccess(sysRoleManager.findSysRoleById(id));
    }

    /**
     * 分页查询角色列表
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<SysRoleVO>> page(SysRoleQuery query) {
        return ResponseResult.ofSuccess(sysRoleManager.pageSysRoles(query));
    }

    /**
     * 设置角色菜单
     *
     * @param form
     * @return
     */
    @PostMapping("/setSysMenuForRole")
    public ResponseResult<Void> setSysMenuForRole(@RequestBody SysRoleMenuAddForm form) {
        sysRoleManager.setSysMenuForRole(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 设置角色api
     *
     * @param form
     * @return
     */
    @PostMapping("/setSysApiForRole")
    public ResponseResult<Void> setSysApiForRole(@RequestBody SysRoleApiAddForm form) {
        sysRoleManager.setSysApiForRole(form);
        return ResponseResult.ofSuccess();
    }


    /**
     * 取消角色接口权限
     *
     * @param form
     * @return
     */
    @PostMapping("/cancelSysApiForRole")
    public ResponseResult<Void> cancelSysApiForRole(@Valid @RequestBody SysRoleApiAddForm form) {
        sysRoleManager.cancelSysApiForRole(form);
        return ResponseResult.ofSuccess();
    }


    /**
     * 根据角色编码分页获取角色的api信息
     *
     * @param query
     * @return
     */
    @GetMapping("/pageSelectRoleApi")
    public ResponseResult<PageResult<SelectRoleApiVo.ApiInfoVo>> pageSelectRoleApi(SelectRoleApiQuery query) {
        return ResponseResult.ofSuccess(sysRoleManager.pageSelectRoleApi(query));
    }

}
