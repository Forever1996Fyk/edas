package com.ahcloud.edas.pulsar.api.controller;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.pulsar.core.application.manager.NamespaceManager;
import com.ahcloud.edas.pulsar.core.domain.namespace.form.NamespaceAddForm;
import com.ahcloud.edas.pulsar.core.domain.namespace.form.NamespaceUpdateForm;
import com.ahcloud.edas.pulsar.core.domain.namespace.query.NamespaceQuery;
import com.ahcloud.edas.pulsar.core.domain.namespace.vo.NamespaceDetailVO;
import com.ahcloud.edas.pulsar.core.domain.namespace.vo.NamespaceVO;
import org.apache.pulsar.client.admin.PulsarAdminException;
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
 * @create: 2024/2/19 10:21
 **/
@RestController
@RequestMapping("/namespace")
public class NamespaceController {
    @Resource
    private NamespaceManager namespaceManager;

    /**
     * 新增命名空间
     *
     * @param form
     * @return
     * @throws PulsarAdminException
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid NamespaceAddForm form) throws PulsarAdminException {
        namespaceManager.addNamespace(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 修改命名空间
     *
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid NamespaceUpdateForm form) throws PulsarAdminException {
        namespaceManager.updateNamespace(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 根据id查询命名空间详情
     *
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<NamespaceDetailVO> findById(@PathVariable("id") Long id) {
        return ResponseResult.ofSuccess(namespaceManager.findById(id));
    }

    /**
     * 根据id删除命名空间
     *
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> deleteById(@PathVariable("id") Long id) throws PulsarAdminException {
        namespaceManager.deleteNamespace(id);
        return ResponseResult.ofSuccess();
    }

    /**
     * 分页查询命名空间列表
     *
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<NamespaceVO>> page(NamespaceQuery query) {
        return ResponseResult.ofSuccess(namespaceManager.pageNamespaceList(query));
    }
}
