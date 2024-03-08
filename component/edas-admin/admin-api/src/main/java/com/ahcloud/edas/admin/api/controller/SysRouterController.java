package com.ahcloud.edas.admin.api.controller;

import com.ahcloud.edas.admin.biz.application.manager.SysUserManager;
import com.ahcloud.edas.admin.biz.domain.menu.vo.RouterPermissionVO;
import com.ahcloud.edas.admin.biz.domain.menu.vo.RouterVo;
import com.ahcloud.edas.common.domain.common.ResponseResult;
import com.ahcloud.edas.uaa.client.UserUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-02 14:14
 **/
@RestController
@RequestMapping("/router")
public class SysRouterController {
    @Resource
    private SysUserManager sysUserManager;

    /**
     * 根据当前登录人id获取菜单路由信息
     * @return
     */
    
    @GetMapping("/listRouterVoByUserId")
    public ResponseResult<RouterPermissionVO> listRouterVoByUserId() {
        Long currentUserId = UserUtils.getUserIdBySession();
        return ResponseResult.ofSuccess(sysUserManager.listRouterVoByUserId(currentUserId));
    }
}
