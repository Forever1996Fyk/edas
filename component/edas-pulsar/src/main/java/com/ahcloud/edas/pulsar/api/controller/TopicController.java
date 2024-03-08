package com.ahcloud.edas.pulsar.api.controller;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.pulsar.core.application.manager.TopicManager;
import com.ahcloud.edas.pulsar.core.domain.subscription.form.SubscriptionAssignForm;
import com.ahcloud.edas.pulsar.core.domain.topic.form.TopicAddForm;
import com.ahcloud.edas.pulsar.core.domain.topic.form.TopicUpdateForm;
import com.ahcloud.edas.pulsar.core.domain.topic.query.TopicQuery;
import com.ahcloud.edas.pulsar.core.domain.topic.vo.TopicDetailVO;
import com.ahcloud.edas.pulsar.core.domain.topic.vo.TopicProduceVO;
import com.ahcloud.edas.pulsar.core.domain.topic.vo.TopicVO;
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
 * @create: 2024/2/19 15:47
 **/
@RestController
@RequestMapping("/topic")
public class TopicController {
    @Resource
    private TopicManager topicManager;

    /**
     * 新增topic
     * @param form
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestBody @Valid TopicAddForm form) throws PulsarAdminException {
        topicManager.addTopic(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 修改topic
     * @param form
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody @Valid TopicUpdateForm form) throws PulsarAdminException {
        topicManager.updateTopic(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 根据id查询topic详情
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<TopicVO> findById(@PathVariable("id") Long id){
        return ResponseResult.ofSuccess(topicManager.findTopicById(id));
    }

    /**
     * 根据id删除topic
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}/{force}")
    public ResponseResult<Void> deleteById(@PathVariable("id") Long id, @PathVariable("force") boolean force) throws PulsarAdminException {
        topicManager.deleteTopic(id, force);
        return ResponseResult.ofSuccess();
    }

    /**
     * 分页查询topic列表
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<TopicVO>> page(TopicQuery query) {
        return ResponseResult.ofSuccess(topicManager.pageTopicList(query));
    }

    /**
     * 查询topic详情
     * @param topicId
     * @return
     * @throws PulsarAdminException
     */
    @GetMapping("/findTopicDetail/{topicId}")
    public ResponseResult<TopicDetailVO> findTopicDetail(@PathVariable("topicId") Long topicId) throws PulsarAdminException {
        return ResponseResult.ofSuccess(topicManager.findTopicDetail(topicId));
    }

    /**
     * 分配订阅权限
     * @param form
     * @return
     * @throws PulsarAdminException
     */
    @PostMapping("/assignSubscriptionPermission")
    public ResponseResult<Void> assignSubscriptionPermission(@RequestBody @Valid SubscriptionAssignForm form) throws PulsarAdminException {
        topicManager.assignSubscriptionPermission(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 查询主题生产者列表
     * @param topicId
     * @return
     */
    @GetMapping("/listTopicProduces/{topicId}")
    public ResponseResult<List<TopicProduceVO>> listTopicProduces(@PathVariable("topicId") Long topicId) throws PulsarAdminException {
        return ResponseResult.ofSuccess(topicManager.listTopicProduces(topicId));
    }
}
