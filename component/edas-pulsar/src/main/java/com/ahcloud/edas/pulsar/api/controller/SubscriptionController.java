package com.ahcloud.edas.pulsar.api.controller;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.pulsar.core.application.manager.SubscriptionManager;
import com.ahcloud.edas.pulsar.core.domain.subscription.form.SubscriptionAddForm;
import com.ahcloud.edas.pulsar.core.domain.subscription.form.SubscriptionOffsetForm;
import com.ahcloud.edas.pulsar.core.domain.subscription.form.SubscriptionUpdateForm;
import com.ahcloud.edas.pulsar.core.domain.subscription.query.SubscriptionQuery;
import com.ahcloud.edas.pulsar.core.domain.subscription.vo.ConsumeProcessVO;
import com.ahcloud.edas.pulsar.core.domain.subscription.vo.SubscriptionConsumeVO;
import com.ahcloud.edas.pulsar.core.domain.subscription.vo.SubscriptionVO;
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
 * @create: 2024/2/20 10:38
 **/
@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    @Resource
    private SubscriptionManager subscriptionManager;

    /**
     * 新增subscription
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid SubscriptionAddForm form) throws PulsarAdminException {
        subscriptionManager.addSubscription(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 更新subscription
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid SubscriptionUpdateForm form) throws PulsarAdminException {
        subscriptionManager.updateSubscription(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 根据id查询subscription详情
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<SubscriptionVO> findById(@PathVariable("id") Long id){
        return ResponseResult.ofSuccess(subscriptionManager.findById(id));
    }

    /**
     * 根据id删除subscription
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}/{force}")
    public ResponseResult<Void> deleteById(@PathVariable("id") Long id, @PathVariable("force") boolean force) throws PulsarAdminException {
        subscriptionManager.deleteSubscription(id, force);
        return ResponseResult.ofSuccess();
    }

    /**
     * 分页查询subscription列表
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<SubscriptionVO>> page(SubscriptionQuery query) throws PulsarAdminException {
        return ResponseResult.ofSuccess(subscriptionManager.pageSubscriptionList(query));
    }

    /**
     * 查询订阅消费者实例列表
     * @param subscriptionId
     * @return
     */
    @GetMapping("/listSubscriptionConsumes/{subscriptionId}")
    public ResponseResult<List<SubscriptionConsumeVO>> listSubscriptionConsumes(@PathVariable("subscriptionId") Long subscriptionId) throws PulsarAdminException {
        return ResponseResult.ofSuccess(subscriptionManager.listSubscriptionConsumes(subscriptionId));
    }

    /**
     * 消费进度
     * @param subscriptionId
     * @return
     */
    @GetMapping("/listConsumeProcess/{subscriptionId}")
    public ResponseResult<List<ConsumeProcessVO>> listConsumeProcess(@PathVariable("subscriptionId") Long subscriptionId) throws PulsarAdminException {
        return ResponseResult.ofSuccess(subscriptionManager.listConsumeProcess(subscriptionId));
    }

    /**
     * offset设置
     * @param form
     * @return
     */
    @PostMapping("/offset")
    public ResponseResult<Void> offset(@RequestBody @Valid SubscriptionOffsetForm form) throws PulsarAdminException {
        subscriptionManager.offset(form);
        return ResponseResult.ofSuccess();
    }
}
