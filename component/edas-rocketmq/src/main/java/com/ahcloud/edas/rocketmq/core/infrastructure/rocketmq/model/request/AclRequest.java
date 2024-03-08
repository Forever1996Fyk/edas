/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ahcloud.edas.rocketmq.core.infrastructure.rocketmq.model.request;

import lombok.Data;
import org.apache.rocketmq.common.PlainAccessConfig;

/**
 * @program: edas
 * @description: acl
 * @author: YuKai Fan
 * @create: 2023/12/29 11:08
 **/
@Data
public class AclRequest {

    /**
     * 默认acl配置
     */
    private PlainAccessConfig config;

    /**
     * topic权限
     */
    private String topicPerm;

    /**
     * group权限
     */
    private String groupPerm;
}
