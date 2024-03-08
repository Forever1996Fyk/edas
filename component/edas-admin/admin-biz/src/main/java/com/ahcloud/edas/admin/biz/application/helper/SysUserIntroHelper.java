package com.ahcloud.edas.admin.biz.application.helper;

import com.ahcloud.edas.admin.biz.domain.user.intro.form.SysUserIntroAddForm;
import com.ahcloud.edas.admin.biz.domain.user.intro.form.SysUserIntroUpdateForm;
import com.ahcloud.edas.admin.biz.domain.user.intro.vo.SysUserIntroVo;
import com.ahcloud.edas.admin.biz.infrastructure.repository.bean.SysUserIntro;
import com.ahcloud.edas.uaa.client.UserUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-19 19:07
 **/
@Component
public class SysUserIntroHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysUserIntro convert(SysUserIntroAddForm form) {
        SysUserIntro sysUserIntro = Convert.INSTANCE.convert(form);
        sysUserIntro.setUserId(UserUtils.getUserIdBySession());
        sysUserIntro.setCreator(UserUtils.getUserNameBySession());
        sysUserIntro.setModifier(UserUtils.getUserNameBySession());
        return sysUserIntro;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysUserIntro convert(SysUserIntroUpdateForm form) {
        return Convert.INSTANCE.convert(form);
    }

    /**
     * 数据转换
     * @param sysUserIntro
     * @return
     */
    public SysUserIntroVo convertToVo(SysUserIntro sysUserIntro) {
        return Convert.INSTANCE.convertToVo(sysUserIntro);
    }

    @Mapper
    public interface Convert {
        Convert INSTANCE = Mappers.getMapper(Convert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        SysUserIntro convert(SysUserIntroAddForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        @Mappings({
                @Mapping(target = "birthDay", expression = "java(com.ahcloud.edas.common.util.DateUtils.parse(form.getBirthDay()))")
        })
        SysUserIntro convert(SysUserIntroUpdateForm form);

        /**
         * 数据转换
         * @param sysUserIntro
         * @return
         */
        SysUserIntroVo convertToVo(SysUserIntro sysUserIntro);
    }
}
