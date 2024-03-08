package com.ahcloud.edas.admin.api.controller;

import com.ahcloud.edas.admin.biz.application.manager.SysMenuManager;
import com.ahcloud.edas.admin.biz.domain.menu.form.SysMenuAddForm;
import com.ahcloud.edas.admin.biz.domain.menu.form.SysMenuApiAddForm;
import com.ahcloud.edas.admin.biz.domain.menu.form.SysMenuUpdateForm;
import com.ahcloud.edas.admin.biz.domain.menu.query.SelectMenuApiQuery;
import com.ahcloud.edas.admin.biz.domain.menu.query.SysMenuButtonPermissionQuery;
import com.ahcloud.edas.admin.biz.domain.menu.query.SysMenuQuery;
import com.ahcloud.edas.admin.biz.domain.menu.query.SysMenuTreeSelectQuery;
import com.ahcloud.edas.admin.biz.domain.menu.tree.SysMenuTreeSelectVo;
import com.ahcloud.edas.admin.biz.domain.menu.tree.SysMenuTreeVo;
import com.ahcloud.edas.admin.biz.domain.menu.vo.SelectMenuApiVo;
import com.ahcloud.edas.admin.biz.domain.menu.vo.SysMenuButtonPermissionVo;
import com.ahcloud.edas.admin.biz.domain.menu.vo.SysMenuVo;
import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @program: permissions-center
 * @description: 系统菜单controller
 * @author: YuKai Fan
 * @create: 2022-03-22 00:20
 **/
@RestController
@RequestMapping("/menu")
public class SysMenuController {
    @Resource
    private SysMenuManager sysMenuManager;
    /**
     * 添加权限菜单
     * @param form
     * @return
     */
    
    @PostMapping("/add")
    public ResponseResult<Void> add(@Valid @RequestBody SysMenuAddForm form) {
        sysMenuManager.addSysMenu(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 更新权限菜单
     * @param form
     * @return
     */
    
    @PostMapping("/update")
    public ResponseResult<Void> update(@Valid @RequestBody SysMenuUpdateForm form) {
        sysMenuManager.updateSysMenu(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 根据id删除权限菜单
     *
     * @param id
     * @return
     */
    
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> delete(@PathVariable(value = "id") Long id) {
        sysMenuManager.deleteSysMenuById(id);
        return ResponseResult.ofSuccess();
    }

    /**
     * 根据id查询权限菜单信息
     *
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<SysMenuVo> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ofSuccess(sysMenuManager.findSysMenuById(id));
    }

    /**
     * 获取菜单树列表
     * @param query
     * @return
     */
    @GetMapping("/listSysMenuTree")
    public ResponseResult<List<SysMenuTreeVo>> listSysMenuTree(SysMenuQuery query) {
        return ResponseResult.ofSuccess(sysMenuManager.listSysMenuTree(query));
    }

    /**
     * 获取菜单树形选择结构列表
     *
     * @param query
     * @return
     */
    @GetMapping("/listMenuSelectTree")
    public ResponseResult<List<SysMenuTreeSelectVo>> listMenuSelectTree(SysMenuTreeSelectQuery query) {
        return ResponseResult.ofSuccess(sysMenuManager.listMenuSelectTree(query));
    }

    /**
     * 设置菜单api
     *
     * @param form
     * @return
     */
    
    @PostMapping("/setSysApiForMenu")
    public ResponseResult<Void> setSysApiForMenu(@Valid @RequestBody SysMenuApiAddForm form) {
        sysMenuManager.setSysApiForMenu(form);
        return ResponseResult.ofSuccess();
    }


    /**
     * 取消角色接口权限
     * @param form
     * @return
     */
    
    @PostMapping("/cancelSysApiForMenu")
    public ResponseResult<Void> cancelSysApiForMenu(@Valid @RequestBody SysMenuApiAddForm form) {
        sysMenuManager.cancelSysApiForMenu(form);
        return ResponseResult.ofSuccess();
    }


    /**
     * 根据菜单编码分页获取菜单的api信息
     * @param query
     * @return
     */
    @GetMapping("/pageSelectMenuApi")
    public ResponseResult<PageResult<SelectMenuApiVo.ApiInfoVo>> pageSelectRoleApi(SelectMenuApiQuery query) {
        return ResponseResult.ofSuccess(sysMenuManager.pageSelectMenuApi(query));
    }

    /**
     * 查询按钮权限集合
     * @param query
     * @return
     */
    @GetMapping("/listSysMenuButtonPermissionVo")
    public ResponseResult<List<SysMenuButtonPermissionVo>> listSysMenuButtonPermissionVo(SysMenuButtonPermissionQuery query) {
        return ResponseResult.ofSuccess(sysMenuManager.listSysMenuButtonPermissionVo(query));
    }

//    /**
//     * 查询按钮权限集合
//     * @param query
//     * @return
//     */
//    @GetMapping("/listSysMenuButtonPermissionVo")
//    public ResponseResult<Map<String, Boolean>> getButtonPermissionMap(SysMenuButtonPermissionQuery query) {
//        return ResponseResult.ofSuccess(sysMenuManager.getButtonPermissionMap(query));
//    }
}
