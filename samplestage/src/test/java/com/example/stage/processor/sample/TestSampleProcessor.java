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

import _ss_com.fasterxml.jackson.databind.ObjectMapper;
import _ss_com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.streamsets.pipeline.api.Field;
import com.streamsets.pipeline.api.Record;
import com.streamsets.pipeline.api.StageException;
import com.streamsets.pipeline.sdk.ProcessorRunner;
import com.streamsets.pipeline.sdk.RecordCreator;
import com.streamsets.pipeline.sdk.StageRunner;
import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TestSampleProcessor {
  @Test
  @SuppressWarnings("unchecked")
  public void testProcessor() throws StageException, IOException {
    ProcessorRunner runner = new ProcessorRunner.Builder(SampleDProcessor.class)
        .addConfiguration("config", "value")
        .addOutputLane("output")
        .build();

    runner.runInit();
    List<Record> records = createRecord();

    try {
      Record record = RecordCreator.create();
      record.set(Field.create(true));
      StageRunner.Output output = runner.runProcess(records);
      //StageRunner.Output output = runner.runProcess(Arrays.asList(record));
      Assert.assertEquals(1, output.getRecords().get("output").size());
      Assert.assertEquals(true, output.getRecords().get("output").get(0).get().getValueAsBoolean());
    } finally {
      runner.runDestroy();
    }
  }

  public List<Record> createRecord() throws IOException {
    String datestr = "20190919";
    //System.out.println("Bigdecimal value " + new BigDecimal(" "));
    //BigDecimal b = new BigDecimal("123");
    //System.out.println("b" + b);
//    InputStream inputStream = new FileInputStream(new File("student.yml"));
//
//    Yaml yaml = new Yaml();
//    Map<String, Object>data = (Map<String, Object>) yaml.load(inputStream);
//    System.out.println(data);
//
//    ClassLoader classLoader = getClass().getClassLoader();
//    File yamlfile = new File(classLoader.getResource("test.yaml").getFile());
//    ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    LocalDate date = LocalDate.parse(datestr, formatter);
    System.out.println("Hello");
    //Date date1 = Date(date)
    System.out.println("date : " + date);
    File file = new File("/Users/rajeevnandan/Desktop/streamsets/sample_data/test.csv");
    BufferedReader br = new BufferedReader(new FileReader(file));
    String str;
    List<Record> records = new ArrayList<>();
    while ((str = br.readLine()) != null) {
      Map<String, Field> map = new HashMap<>();
      Record record = RecordCreator.create();
      map.put("text", Field.create(str));
      record.set(Field.create(map));
      records.add(record);
      System.out.println(str);
    }
    return records;
  }
}
