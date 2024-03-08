package com.ahcloud.edas.pulsar.api.controller;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.pulsar.core.application.manager.TenantManager;
import com.ahcloud.edas.pulsar.core.domain.tenant.form.TenantAddForm;
import com.ahcloud.edas.pulsar.core.domain.tenant.form.TenantUpdateForm;
import com.ahcloud.edas.pulsar.core.domain.tenant.query.TenantQuery;
import com.ahcloud.edas.pulsar.core.domain.tenant.vo.TenantDetailVO;
import com.ahcloud.edas.pulsar.core.domain.tenant.vo.TenantSelectVO;
import com.ahcloud.edas.pulsar.core.domain.tenant.vo.TenantVO;
import org.apache.pulsar.client.admin.PulsarAdminException;
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
 * @create: 2024/2/18 17:00
 **/
@RestController
@RequestMapping("/tenant")
public class TenantController {
    @Resource
    private TenantManager tenantManager;

    /**
     * 新增租户
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid TenantAddForm form) throws PulsarAdminException {
        tenantManager.addTenant(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 修改租户
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid TenantUpdateForm form) throws PulsarAdminException {
        tenantManager.updateTenant(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 根据id查询租户详情
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<TenantDetailVO> findById(@PathVariable("id") Long id){
        return ResponseResult.ofSuccess(tenantManager.findById(id));
    }

    /**
     * 根据id删除租户
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> deleteById(@PathVariable("id") Long id) throws PulsarAdminException {
        tenantManager.deleteTenant(id);
        return ResponseResult.ofSuccess();
    }

    /**
     * 分页查询租户列表
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<TenantVO>> page(TenantQuery query) {
        return ResponseResult.ofSuccess(tenantManager.pageTenantList(query));
    }

    /**
     * 查询租户选择列表
     * @return
     */
    @GetMapping("/getTenantSelectList")
    public ResponseResult<List<TenantSelectVO>> getTenantSelectList() {
        return ResponseResult.ofSuccess(tenantManager.getTenantSelectList());
    }
}
