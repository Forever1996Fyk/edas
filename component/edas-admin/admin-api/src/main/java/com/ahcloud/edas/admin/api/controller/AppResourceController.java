package com.ahcloud.edas.admin.api.controller;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.admin.biz.application.manager.AppResourceManager;
import com.ahcloud.edas.admin.biz.domain.resource.form.ResourceAddForm;
import com.ahcloud.edas.admin.biz.domain.resource.form.ResourceUpdateForm;
import com.ahcloud.edas.admin.biz.domain.resource.query.ResourceQuery;
import com.ahcloud.edas.admin.biz.domain.resource.vo.ResourceDetailsVO;
import com.ahcloud.edas.admin.biz.domain.resource.vo.ResourceVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2023/12/6 15:55
 **/
@RestController
@RequestMapping("/app/resource")
public class AppResourceController {
    @Resource
    private AppResourceManager appResourceManager;

    /**
     * 添加资源
     *
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@Valid @RequestBody ResourceAddForm form) {
        appResourceManager.addResource(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 编辑资源
     *
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@Valid @RequestBody ResourceUpdateForm form) {
        appResourceManager.updateResource(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 根据id删除资源
     *
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> delete(@PathVariable(value = "id") Long id) {
        appResourceManager.deleteResourceById(id);
        return ResponseResult.ofSuccess();
    }

    /**
     * 根据id查询资源信息
     *
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<ResourceVO> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ofSuccess(appResourceManager.findResourceById(id));
    }

    /**
     * 根据id查询资源信息
     *
     * @param id
     * @return
     */
    @GetMapping("/findDetailById/{id}")
    public ResponseResult<ResourceDetailsVO> findDetailById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ofSuccess(appResourceManager.findResourceDetailById(id));
    }

    /**
     * 分页查询资源列表
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<ResourceVO>> page(ResourceQuery query) {
        return ResponseResult.ofSuccess(appResourceManager.pageResourceList(query));
    }
}
