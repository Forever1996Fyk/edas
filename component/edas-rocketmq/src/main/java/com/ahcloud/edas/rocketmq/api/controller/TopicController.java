package com.ahcloud.edas.rocketmq.api.controller;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.rocketmq.core.application.manager.TopicManager;
import com.ahcloud.edas.rocketmq.core.domain.subscribe.vo.SubscribeGroupConsumeDetailVO;
import com.ahcloud.edas.rocketmq.core.domain.topic.form.TopicAddForm;
import com.ahcloud.edas.rocketmq.core.domain.topic.form.TopicUpdateForm;
import com.ahcloud.edas.rocketmq.core.domain.topic.query.TopicQuery;
import com.ahcloud.edas.rocketmq.core.domain.topic.vo.TopicVO;
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
 * @create: 2023/12/29 11:03
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
    public ResponseResult<Void> add(@RequestBody @Valid TopicAddForm form) {
        topicManager.addTopic(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 更新topic
     * @param form
     * @return
     */
    @PostMapping("/updateById")
    public ResponseResult<Void> updateById(@RequestBody @Valid TopicUpdateForm form) {
        topicManager.updateTopic(form);
        return ResponseResult.ofSuccess();
    }

    /**
     * 删除topic
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public ResponseResult<Void> deleteById(@PathVariable("id") Long id) {
        topicManager.deleteById(id);
        return ResponseResult.ofSuccess();
    }

    /**
     * 查询topic详情
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    public ResponseResult<TopicVO> findById(@PathVariable("id") Long id) {
        return ResponseResult.ofSuccess(topicManager.findById(id));
    }

    /**
     * 分页查询topic
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<TopicVO>> page(TopicQuery query) {
        return ResponseResult.ofSuccess(topicManager.pageTopicList(query));
    }

    /**
     * 查询订阅组消费详情
     * @param topicId
     * @param groupName
     * @return
     */
    @GetMapping("/findSubscribeGroupConsumeDetail")
    public ResponseResult<SubscribeGroupConsumeDetailVO> findSubscribeGroupConsumeDetail(@RequestParam("topicId") Long topicId, @RequestParam("groupName") String groupName) {
        return ResponseResult.ofSuccess(topicManager.findSubscribeGroupConsumeDetail(topicId, groupName));
    }

    /**
     * 查询你订阅组列表
     * @param topicId
     * @return
     */
    @GetMapping("/getSelectSubscribeList/{topicId}")
    public ResponseResult<List<String>> getSelectSubscribeList(@PathVariable("topicId") Long topicId) {
        return ResponseResult.ofSuccess(topicManager.getSelectSubscribeList(topicId));
    }

    /**
     * 获取broker选择列表
     * @return
     */
    @GetMapping("/getSelectBrokerNameList")
    public ResponseResult<List<String>> getSelectBrokerNameList() {
        return ResponseResult.ofSuccess(topicManager.getSelectBrokerNameList());
    }
}
