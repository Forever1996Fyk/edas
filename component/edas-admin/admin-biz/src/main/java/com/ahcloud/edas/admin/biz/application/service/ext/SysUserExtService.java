package com.ahcloud.edas.admin.biz.application.service.ext;

import com.ahcloud.edas.admin.biz.domain.user.query.SysUserExportQuery;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.session.ResultHandler;

import java.util.Collection;
import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-11 10:51
 **/
public interface SysUserExtService extends IService<SysUser> {

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    SysUser getOneByUserId(Long userId);

    /**
     * 根据用户id获取用户集合
     * @param userIds
     * @return
     */
    Set<SysUser> listByUserId(Collection<Long> userIds);

    /**
     * 游标查询导出列表
     * @param query
     * @param resultHandler
     */
    void streamQueryForExport(SysUserExportQuery query, ResultHandler<SysUser> resultHandler);
}
