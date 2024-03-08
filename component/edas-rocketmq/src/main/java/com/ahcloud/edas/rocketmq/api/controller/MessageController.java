package com.ahcloud.edas.rocketmq.api.controller;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.rocketmq.core.application.manager.MessageManager;
import com.ahcloud.edas.rocketmq.core.domain.message.query.RmqMessageQuery;
import com.ahcloud.edas.rocketmq.core.domain.message.vo.ConsumeResult;
import com.ahcloud.edas.rocketmq.core.domain.message.vo.MessageDetailVO;
import com.ahcloud.edas.rocketmq.core.domain.message.vo.MessageVO;
import com.ahcloud.edas.rocketmq.core.domain.message.vo.RmqMessageVOPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/1/11 11:20
 **/
@RestController
@RequestMapping("/message")
public class MessageController {
    @Resource
    private MessageManager messageManager;

    /**
     * 分页查询消息列表
     * @param query
     * @return
     */
    @GetMapping("/page")
    public ResponseResult<RmqMessageVOPage> pageMessageList(RmqMessageQuery query) {
        return ResponseResult.ofSuccess(messageManager.pageMessageList(query));
    }

    /**
     * 根据消息id查询消息详情
     * @param topicId
     * @param messageId
     * @return
     */
    @GetMapping("/findById")
    public ResponseResult<MessageDetailVO> findMessageDetailById(@RequestParam Long topicId, @RequestParam String messageId) {
        return ResponseResult.ofSuccess(messageManager.findMessageDetailById(topicId, messageId));
    }

    /**
     * 查询消费消息结果
     * @param query
     * @return
     */
    @GetMapping("/consumeMessageDirectly")
    public ResponseResult<ConsumeResult> consumeMessageDirectly(RmqMessageQuery query) {
        return ResponseResult.ofSuccess(messageManager.consumeMessageDirectly(query));
    }
}
