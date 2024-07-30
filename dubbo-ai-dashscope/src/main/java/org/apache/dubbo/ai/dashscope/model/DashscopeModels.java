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
package org.apache.dubbo.ai.dashscope.model;

import com.alibaba.fastjson2.JSONObject;
import org.apache.dubbo.ai.core.chat.model.ChatModel;
import org.apache.dubbo.ai.core.config.AiModelProviderConfig;
import org.apache.dubbo.ai.core.config.Configs;
import org.apache.dubbo.ai.core.model.AiModels;
import org.apache.dubbo.ai.dashscope.chat.model.DashscopeChatModel;
import org.apache.dubbo.ai.spring.ai.dashscope.DashscopeChatOptions;
import org.apache.dubbo.ai.spring.ai.dashscope.api.DashscopeApi;

import java.util.HashMap;
import java.util.Map;

public class DashscopeModels implements AiModels {

    private final Map<String, DashscopeApi> cachedApi = new HashMap<>();


    @Override
    public ChatModel getChatModel(String configModelName, JSONObject chatOptions) {
        DashscopeApi dashscopeApi = getDashscopeApi(configModelName);
        return new DashscopeChatModel(dashscopeApi, chatOptions.to(DashscopeChatOptions.class));
    }


    private DashscopeApi getDashscopeApi(String name) {
        if (!cachedApi.containsKey(name)) {
            cachedApi.put(name, buildApi(name));
        }
        return cachedApi.get(name);
    }

    private DashscopeApi buildApi(String name) {
        AiModelProviderConfig aiModelProviderConfig = Configs.buildFromConfigurations(name);
        String providerCompany = aiModelProviderConfig.getProviderCompany();
        if (!providerCompany.equals("dashscope")) {
            throw new RuntimeException("not support company");
        }
        DashscopeApi dashscopeApi;
        String baseUrl = aiModelProviderConfig.getBaseUrl();
        String secretKey = aiModelProviderConfig.getSecretKey();
        if (baseUrl != null) {
            dashscopeApi = new DashscopeApi(baseUrl, secretKey);
        } else {
            dashscopeApi = new DashscopeApi(secretKey);
        }
        return dashscopeApi;
    }
    
}
