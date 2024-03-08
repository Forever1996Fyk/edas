package com.ahcloud.edas.admin.api.controller;

import com.ahcloud.edas.admin.biz.application.manager.SysDeptManager;
import com.ahcloud.edas.admin.biz.domain.dept.form.DeptAddForm;
import com.ahcloud.edas.admin.biz.domain.dept.form.DeptUpdateForm;
import com.ahcloud.edas.admin.biz.domain.dept.query.SysDeptQuery;
import com.ahcloud.edas.admin.biz.domain.dept.query.SysDeptTreeSelectQuery;
import com.ahcloud.edas.admin.biz.domain.dept.vo.SysDeptTreeSelectVo;
import com.ahcloud.edas.admin.biz.domain.dept.vo.SysDeptTreeVo;
import com.ahcloud.edas.admin.biz.domain.dept.vo.SysDeptVo;
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
 * @create: 2022-07-19 09:48
 **/
@RestController
@RequestMapping("/dept")
public class SysDeptController {
    @Resource
    private SysDeptManager sysDeptManager;

    /**
     * 添加部门
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid DeptAddForm form) {
        sysDeptManager.addDept(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 更新部门
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid DeptUpdateForm form) {
        sysDeptManager.updateSysDept(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> deleteById(@PathVariable(value = "id") Long id) {
        sysDeptManager.deleteSysDept(id);
        return ResponseResult.ofSuccess();
    }

    /**
     * 查询部门
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<SysDeptVo> findById(@PathVariable(value = "id") Long id) {
        return ResponseResult.ofSuccess(sysDeptManager.findById(id));
    }

    /**
     * 查询部门树
     * @param query
     * @return
     */
    @GetMapping("/listSysDeptTree")
    public ResponseResult<List<SysDeptTreeVo>> listSysDeptTree(SysDeptQuery query) {
        return ResponseResult.ofSuccess(sysDeptManager.listSysDeptTree(query));
    }

    /**
     * 获取部门树形选择结构列表
     *
     * @param query
     * @return
     */
    @GetMapping("/listDeptSelectTree")
    public ResponseResult<List<SysDeptTreeSelectVo>> listDeptSelectTree(SysDeptTreeSelectQuery query) {
        return ResponseResult.ofSuccess(sysDeptManager.listDeptSelectTree(query));
    }

}
