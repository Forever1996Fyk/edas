package com.ahcloud.edas.pulsar.api.controller;

import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.pulsar.core.application.manager.BookieManager;
import com.ahcloud.edas.pulsar.core.domain.bookie.vo.BookieVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: edas
 * @description: bookie就是BookKeeper, 需要单独部署才能使用，Apache BookKeeper <a href="https://bookkeeper.apache.org/">...</a>
 * @author: YuKai Fan
 * @create: 2024/2/27 10:02
 **/
@RestController
@RequestMapping("/bookie")
public class BookieController {
    @Resource
    private BookieManager bookieManager;

    /**
     * 查询bookie列表
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<List<BookieVO>> listBookies() {
        return ResponseResult.ofSuccess(bookieManager.listBookies());
    }

    /**
     * 跳转bookie 心跳链接
     * @param bookie
     * @return
     */
    @GetMapping("/heartbeat/{bookie}")
    public ResponseResult<String> bookieHeartbeat(@PathVariable("bookie") String bookie) {
        return ResponseResult.ofSuccess(bookieManager.forwardBookiesHeartbeat(bookie));
    }
}
