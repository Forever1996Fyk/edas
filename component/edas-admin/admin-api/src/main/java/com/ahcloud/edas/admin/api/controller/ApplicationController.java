package com.ahcloud.edas.admin.api.controller;

import com.ahcloud.edas.admin.biz.application.manager.ApplicationManager;
import com.ahcloud.edas.admin.biz.domain.app.form.AppAddForm;
import com.ahcloud.edas.admin.biz.domain.app.form.AppScopeForm;
import com.ahcloud.edas.admin.biz.domain.app.form.CancelScopeForm;
import com.ahcloud.edas.admin.biz.domain.app.query.AppBaseQuery;
import com.ahcloud.edas.admin.biz.domain.app.vo.AppBaseVO;
import com.ahcloud.edas.admin.biz.domain.app.vo.AppSelectVO;
import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.uaa.client.UserUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/4 16:59
 **/
@RestController
@RequestMapping("/application")
public class ApplicationController {
    @Resource
    private ApplicationManager applicationManager;

    /**
     * 新增app应用
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> addApplication(@RequestBody @Valid AppAddForm form) {
        applicationManager.addApp(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 删除app应用
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> deleteById(@PathVariable("id") Long id) {
        applicationManager.deleteAppById(id);
        return ResponseResult.ofSuccess();
    }

    /**
     * 分页查询appList
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<AppBaseVO>> page(AppBaseQuery query) {
        return ResponseResult.ofSuccess(applicationManager.pageAppBaseList(query));
    }

    /**
     * 分配app权限
     * @param form
     * @return
     */
    @PostMapping("/assign")
    public ResponseResult<Void> assign(@RequestBody @Valid AppScopeForm form) {
        applicationManager.assignAppScope(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 取消用户权限
     * @param form
     * @return
     */
    @PostMapping("/cancelAssign")
    public ResponseResult<Void> cancelAssign(@RequestBody AppScopeForm form) {
        applicationManager.cancelAssign(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 查询用户app权限集合
     * @param query
     * @return
     */
    @GetMapping("/pageUserAppScopeList")
    public ResponseResult<PageResult<AppBaseVO>> pageUserAppScopeList(AppBaseQuery query) {
        return ResponseResult.ofSuccess(applicationManager.pageUserAppScopeList(query));
    }

    /**
     * 查询当前用户app权限集合
     * @param query
     * @return
     */
    @GetMapping("/pageMyAppList")
    public ResponseResult<PageResult<AppBaseVO>> pageMyAppList(AppBaseQuery query) {
        Long userId = UserUtils.getUserIdBySession();
        query.setUserId(userId);
        return ResponseResult.ofSuccess(applicationManager.pageUserAppScopeList(query));
    }

    /**
     * 获取我的app选择列表
     * @param query
     * @return
     */
    @GetMapping("/getMyAppSelectList")
    public ResponseResult<List<AppSelectVO>> getMyAppSelectList(AppBaseQuery query) {
        return ResponseResult.ofSuccess(applicationManager.getMyAppSelectList(query));
    }

    /**
     * 根据appId获取应用详情
     * @param appId
     * @return
     */
    @GetMapping("/findAppByAppId/{appId}")
    public ResponseResult<AppBaseVO> findAppByAppId(@PathVariable("appId") Long appId) {
        return ResponseResult.ofSuccess(applicationManager.findAppByAppId(appId));
    }

    /**
     * 获取用户app选择列表
     * @param userId
     * @param env
     * @return
     */
    @GetMapping("/getAppSelectListByUserId/{userId}/{env}")
    public ResponseResult<List<AppSelectVO>> getAppSelectListByUserId(@PathVariable("userId") Long userId, @PathVariable("env") String env) {
        return ResponseResult.ofSuccess(applicationManager.getAppSelectListByUserId(userId, env));
    }

}
