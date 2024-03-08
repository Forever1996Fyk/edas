package com.ahcloud.edas.admin.api.controller;

import com.ahcloud.edas.admin.biz.application.manager.SysDictDetailManager;
import com.ahcloud.edas.admin.biz.domain.dict.form.SysDictDetailAddForm;
import com.ahcloud.edas.admin.biz.domain.dict.form.SysDictDetailUpdateForm;
import com.ahcloud.edas.admin.biz.domain.dict.query.SysDictDetailQuery;
import com.ahcloud.edas.admin.biz.domain.dict.vo.SelectDictLabelVo;
import com.ahcloud.edas.admin.biz.domain.dict.vo.SysDictDetailVo;
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
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 17:48
 **/
@RestController
@RequestMapping("/dictDetail")
public class SysDictDetailController {
    @Resource
    private SysDictDetailManager sysDictDetailManager;

    /**
     * 添加字典
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid SysDictDetailAddForm form) {
        sysDictDetailManager.addSysDictDetail(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 更新字典
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid SysDictDetailUpdateForm form) {
        sysDictDetailManager.updateSysDictDetail(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 删除字典
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> deleteById(@PathVariable(value = "id") Long id) {
        sysDictDetailManager.deleteById(id);
        return ResponseResult.ofSuccess();
    }

    /**
     * 根据id获取字典
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<SysDictDetailVo> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ofSuccess(sysDictDetailManager.findById(id));
    }

    /**
     * 分页查询字典
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<SysDictDetailVo>> page(SysDictDetailQuery query) {
        return ResponseResult.ofSuccess(sysDictDetailManager.pageSysDictDetailList(query));
    }

    /**
     * 根据字典编码选择标签列表
     * @param dictCode
     * @return
     */
    @GetMapping("/selectDictLabelList/{dictCode}")
    public ResponseResult<List<SelectDictLabelVo>> selectDictLabelList(@PathVariable(value = "dictCode") String dictCode) {
        return ResponseResult.ofSuccess(sysDictDetailManager.selectDictLabelList(dictCode));
    }
}
