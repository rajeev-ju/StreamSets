/**
 * Copyright 2015 StreamSets Inc.
 *
 * Licensed under the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.stage.processor.sample;

import com.streamsets.pipeline.api.*;
import com.streamsets.pipeline.api.base.configurablestage.DProcessor;

import java.util.List;

@StageDef(
    version = 1,
    label = "Pcf File Parser",
    description = "",
    icon = "default.png",
    onlineHelpRefUrl = ""
)
@ConfigGroups(Groups.class)
@GenerateResourceBundle
public class SampleDProcessor extends DProcessor {

//  @ConfigDef(
//      required = true,
//      type = ConfigDef.Type.STRING,
//      defaultValue = "default",
//      label = "Sample Config",
//      displayPosition = 10,
//      group = "HEADER"
//  )
//  public String config;

  @ConfigDef(
          required = true,
          type = ConfigDef.Type.MODEL,
          defaultValue = "",
          label = "Header Details",
          displayPosition = 40,
          group = "HEADER"
  )
  @ListBeanModel
  public List<HeaderConfig> headerConfigs;

  @ConfigDef(
          required = true,
          type = ConfigDef.Type.MODEL,
          defaultValue = "",
          label = "Constituent Details",
          displayPosition = 40,
          group = "CONSTITUENT"
  )
  @ListBeanModel
  public List<HeaderConfig> constituentConfigs;

  /** {@inheritDoc} */
//  @Override
//  public String getConfig() {
//    return config;
//  }

  @Override
  protected Processor createProcessor() {
    return new SampleProcessor(headerConfigs);
  }

}