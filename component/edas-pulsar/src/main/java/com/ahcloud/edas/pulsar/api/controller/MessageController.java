package com.ahcloud.edas.pulsar.api.controller;

import com.ahcloud.edas.common.domain.common.PageResult;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.pulsar.core.application.manager.MessageManager;
import com.ahcloud.edas.pulsar.core.domain.message.query.MessageQuery;
import com.ahcloud.edas.pulsar.core.domain.message.vo.MessageDetailVO;
import com.ahcloud.edas.pulsar.core.domain.message.vo.MessageVO;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: edas
 * @description:
 * @author: YuKai Fan
 * @create: 2024/2/29 17:07
 **/
@RestController
@RequestMapping("/message")
public class MessageController {
    @Resource
    private MessageManager messageManager;

    /**
     * 分页查询消息
     * @param query
     * @return
     * @throws PulsarAdminException
     */
    @GetMapping("/page")
    public ResponseResult<PageResult<MessageVO>> page(MessageQuery query) throws PulsarAdminException {
        return ResponseResult.ofSuccess(messageManager.peekMessageList(query));
    }

    /**
     * 查询消息详情
     * @param query
     * @return
     * @throws PulsarAdminException
     */
    @GetMapping("/findMessageDetail")
    public ResponseResult<MessageDetailVO> findMessageDetail(MessageQuery query) throws PulsarAdminException {
        return ResponseResult.ofSuccess(messageManager.findMessageDetail(query));
    }
}
