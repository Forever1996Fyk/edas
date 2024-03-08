package com.ahcloud.edas.nacos.api.controller;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.nacos.biz.application.manager.NacosConfigManager;
import com.ahcloud.edas.nacos.biz.domain.nacos.dto.HistoryConfigInfo;
import com.ahcloud.edas.nacos.biz.domain.nacos.form.ConfigAddForm;
import com.ahcloud.edas.nacos.biz.domain.nacos.form.ConfigUpdateForm;
import com.ahcloud.edas.nacos.biz.domain.nacos.query.ConfigQuery;
import com.ahcloud.edas.nacos.biz.domain.nacos.vo.ConfigVO;
import com.ahcloud.edas.nacos.biz.domain.nacos.vo.NamespaceSelectVO;
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
 * @create: 2023/12/6 15:55
 **/
@RestController
@RequestMapping("/nacos/config")
public class NacosConfigController {
    @Resource
    private NacosConfigManager nacosConfigManager;

    /**
     * 新增配置
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid ConfigAddForm form) {
        nacosConfigManager.addConfig(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 修改配置
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid ConfigUpdateForm form) {
        nacosConfigManager.updateConfig(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 删除配置
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> deleteById(@PathVariable("id") Long id) {
        nacosConfigManager.deleteConfig(id);
        return ResponseResult.ofSuccess();
    }


    /**
     * 查询配置详情
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<ConfigVO> findById(@PathVariable("id") Long id) {
        return ResponseResult.ofSuccess(nacosConfigManager.findConfigById(id));
    }

    /**
     * 分页查询配置列表
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<ConfigVO>> page(ConfigQuery query) {
        return ResponseResult.ofSuccess(nacosConfigManager.pageConfigList(query));
    }

    /**
     * 分页查询历史记录列表
     * @param query
     * @return
     */
    @GetMapping("/pageHistoryConfigList")
    public ResponseResult<PageResult<HistoryConfigInfo>> pageHistoryConfigList(ConfigQuery query) throws Exception {
        return ResponseResult.ofSuccess(nacosConfigManager.pageHistoryConfigList(query));
    }

    /**
     * 查询历史配置详情
     * @param query
     * @return
     */
    @GetMapping("/findHistoryConfigById")
    public ResponseResult<HistoryConfigInfo> findHistoryConfigById(ConfigQuery query) throws Exception {
        return ResponseResult.ofSuccess(nacosConfigManager.findHistoryConfigById(query));
    }

    /**
     * 版本回退
     * @param query
     * @return
     */
    @GetMapping("/rollback")
    public ResponseResult<Void> historyConfigRollback(ConfigQuery query) throws Exception {
        nacosConfigManager.historyConfigRollback(query);
        return ResponseResult.ofSuccess();
    }

    /**
     * 查询命名空间选择列表
     * @return
     */
    @GetMapping("/getNamespaceSelectList")
    public ResponseResult<List<NamespaceSelectVO>> getNamespaceSelectList() throws Exception {
        return ResponseResult.ofSuccess(nacosConfigManager.getNamespaceSelectList());
    }
}
