package com.ahcloud.edas.gateway.api.controller;

import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.gateway.core.application.manager.GatewayApiMetadataManager;
import com.ahcloud.edas.gateway.core.domain.api.vo.ApiMetadataVO;
import com.ahcloud.edas.gateway.core.domain.api.vo.ServiceIdSelectVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/5/29 17:24
 **/
@RestController
@RequestMapping("/metadata")
public class GatewayApiMetaDataController {
    @Resource
    private GatewayApiMetadataManager gatewayApiMetadataManager;

    /**
     * 根据服务id获取api元数据选择列表
     * @param appId
     * @return
     */
    @GetMapping("/selectApiMetadataList/{appId}")
    public ResponseResult<List<ApiMetadataVO>> selectApiMetadataList(@PathVariable("appId") Long appId) {
        List<ApiMetadataVO> list = gatewayApiMetadataManager.selectApiMetadataList(appId);
        return ResponseResult.ofSuccess(list);
    }
}
