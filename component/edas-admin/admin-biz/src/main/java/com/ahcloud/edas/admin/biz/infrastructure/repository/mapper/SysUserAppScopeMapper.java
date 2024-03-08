package com.ahcloud.edas.admin.biz.infrastructure.repository.mapper;

import com.ahcloud.edas.admin.biz.domain.app.query.AppBaseQuery;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysAppPark;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserAppScope;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户app作用域 Mapper 接口
 * </p>
 *
 * @author auto_generation
 * @since 2023-12-04
 */
public interface SysUserAppScopeMapper extends BaseMapper<SysUserAppScope> {

    /**
     * 查询app信息
     * @param query
     * @return
     */
    @Select("<script>select b.* " +
            " from sys_user_app_scope a left join sys_app_park b on a.app_id = b.app_id " +
            " where a.user_id = #{userId} and a.deleted = 0 and b.deleted = 0" +
            " <if test='appCode != null'> and a.app_code = #{appCode} </if>" +
            " <if test='env != null'> and a.env = #{env} </if>" +
            " <if test='appName != null'> and b.app_name like concat('%', #{appName}, '%') </if>" +
            "</script>")
    List<SysAppPark> selectByQuery(AppBaseQuery query);
}
