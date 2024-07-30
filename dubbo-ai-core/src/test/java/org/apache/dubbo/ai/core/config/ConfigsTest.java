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
package org.apache.dubbo.ai.core.config;

import org.apache.dubbo.ai.core.util.PropertiesUtil;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;



class ConfigsTest {
    
    

    @BeforeAll
    static void setUp() {
        String path = "dubbo-ai-example.properties";
        Map<String, String> props = PropertiesUtil.getPropsByPath(path);
        ApplicationModel.defaultModel().modelEnvironment().updateAppConfigMap(props);
    }
    
    @Test
    void testLoadConfig() {
        AiModelProviderConfig aiModelProviderConfig = Configs.buildFromConfigurations("m1");
    }

}