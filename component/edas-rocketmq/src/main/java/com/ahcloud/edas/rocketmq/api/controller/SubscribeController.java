package com.ahcloud.edas.rocketmq.api.controller;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.rocketmq.core.application.manager.SubscribeManager;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.form.ResetOffsetForm;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.form.SubscribeAddForm;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.form.SubscribeDeleteForm;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.form.SubscribeUpdateForm;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.query.SubscribeGroupQuery;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.query.SubscribeQuery;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.vo.SubscribeGroupConsumeDetailVO;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.vo.SubscribeVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/3 10:29
 **/
@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
    @Resource
    private SubscribeManager subscribeManager;

    /**
     * 新增订阅
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> addSubscribe(@RequestBody @Valid SubscribeAddForm form) {
        subscribeManager.addSubscribe(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 更新订阅
     * @param form
     * @return
     */
    @PostMapping("/updateById")
    public ResponseResult<Void> updateSubscribe(@RequestBody @Valid SubscribeUpdateForm form) {
        subscribeManager.updateSubscribe(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 查询订阅
     * @param id
     * @return
     */
    @GetMapping("/findSubscribeById/{id}")
    public ResponseResult<SubscribeVO> findSubscribeById(@PathVariable("id") Long id) {
        return ResponseResult.ofSuccess(subscribeManager.findSubscribeById(id));
    }

    /**
     * 删除订阅
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> deleteSubscribeById(@PathVariable("id") Long id) {
        subscribeManager.deleteSubscribeById(id);
        return ResponseResult.ofSuccess();
    }

    /**
     * 分页查询订阅
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<SubscribeVO>> deleteSubscribeById(SubscribeQuery query) {
        return ResponseResult.ofSuccess(subscribeManager.pageSubscribeList(query));
    }

    /**
     * 重置消费位点
     * @param form
     * @return
     */
    @PostMapping("/resetOffset")
    public ResponseResult<Void> resetOffset(@RequestBody @Valid ResetOffsetForm form) {
        subscribeManager.resetOffset(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 获取订阅broker列表
     * @param id
     * @return
     */
    @GetMapping("/getSubscriptionBrokerList/{id}")
    public ResponseResult<List<String>> getSubscriptionBrokerList(@PathVariable("id") Long id) {
        return ResponseResult.ofSuccess(subscribeManager.getSubscriptionBrokerList(id));
    }
}
