package com.ahcloud.edas.gateway.core.domain.api.bo;

import com.ahcloud.edas.gateway.core.domain.api.dto.ApiDefinitionDTO;
import lombok.Data;
import org.springframework.web.util.pattern.PathPattern;

/**
 * @program: ahcloud-gateway
 * @description:
 * @author: YuKai Fan
 * @create: 2023/2/21 09:00
 **/
@Data
public class ApiDefinitionPatternDTO {

    /**
     * 路径匹配
     */
    private final PathPattern pathPattern;

    /**
     * api刷新实体
     */
    private final ApiDefinitionDTO apiDefinitionDTO;

    public ApiDefinitionPatternDTO(PathPattern pathPattern, ApiDefinitionDTO apiDefinitionDTO) {
        this.pathPattern = pathPattern;
        this.apiDefinitionDTO = apiDefinitionDTO;
    }
}
