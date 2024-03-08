package com.ahcloud.edas.admin.api.controller;

import com.ahcloud.edas.admin.biz.application.manager.SysDictManager;
import com.ahcloud.edas.admin.biz.domain.dict.form.SysDictAddForm;
import com.ahcloud.edas.admin.biz.domain.dict.form.SysDictUpdateForm;
import com.ahcloud.edas.admin.biz.domain.dict.query.SysDictQuery;
import com.ahcloud.edas.admin.biz.domain.dict.vo.SysDictVo;
import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 17:48
 **/
@RestController
@RequestMapping("/dict")
public class SysDictController {
    @Resource
    private SysDictManager sysDictManager;

    /**
     * 添加字典
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid SysDictAddForm form) {
        sysDictManager.addSysDict(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 更新字典
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid SysDictUpdateForm form) {
        sysDictManager.updateSysDict(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 删除字典
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> deleteById(@PathVariable(value = "id") Long id) {
        sysDictManager.deleteById(id);
        return ResponseResult.ofSuccess();
    }

    /**
     * 根据id获取字典
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<SysDictVo> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ofSuccess(sysDictManager.findById(id));
    }

    /**
     * 分页查询字典
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<SysDictVo>> page(SysDictQuery query) {
        return ResponseResult.ofSuccess(sysDictManager.pageSysDictList(query));
    }
}
