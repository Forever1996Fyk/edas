package com.ahcloud.edas.admin.biz.infrastructure.repository.mapper;

import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysMenu;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 系统用户权限表 Mapper 接口
 * </p>
 *
 * @author auto_generation
 * @since 2022-04-26
 */
public interface SysUserMenuMapper extends BaseMapper<SysUserMenu> {

    /**
     * 查询用户的按钮集合
     * @param userId
     * @return
     */
    @Select("<script>select b.*" +
            " from sys_user_menu a left join sys_menu b on a.menu_id = b.id" +
            " where a.user_id = #{userId} and a.deleted = 0 and b.menu_type = 3 and b.deleted = 0" +
            "</script>")
    List<SysMenu> listButtonMenuByUserId(Long userId);
}
