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

import com.example.stage.lib.sample.Errors;

//import com.oracle.tools.packager.Log;
import com.streamsets.pipeline.api.BatchMaker;
import com.streamsets.pipeline.api.Field;
import com.streamsets.pipeline.api.Record;
import com.streamsets.pipeline.api.StageException;
import com.streamsets.pipeline.api.base.OnRecordErrorException;
import com.streamsets.pipeline.api.base.SingleLaneRecordProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SampleProcessor extends SingleLaneRecordProcessor {
  private static final Logger LOG =  LoggerFactory.getLogger(SampleProcessor.class);
  boolean header = false;
  boolean data = false;
  List<String>headerRecords = new ArrayList<>();

  List<String>dataRecords = new ArrayList<>();
  List<PCF>pcfs = new ArrayList<>();
  private final List<HeaderConfig>headerConfigs;

  public SampleProcessor(List<HeaderConfig>headerConfigs){
    System.out.println(headerConfigs);
    this.headerConfigs = headerConfigs;
  }
  /**
   * Gives access to the UI configuration of the stage provided by the {@link SampleDProcessor} class.
   */
  //public abstract String getConfig();

  /** {@inheritDoc} */
//  @Override
//  protected List<ConfigIssue> init() {
//    // Validate configuration values and open any required resources.
//    List<ConfigIssue> issues = super.init();
//
//    if (getConfig().equals("invalidValue")) {
//      issues.add(
//          getContext().createConfigIssue(
//              Groups.HEADER.name(), "config", Errors.SAMPLE_00, "Here's what's wrong..."
//          )
//      );
//    }
//
//    // If issues is not empty, the UI will inform the user of each configuration issue in the list.
//    return issues;
//  }
//
//  /** {@inheritDoc} */
//  @Override
//  public void destroy() {
//    // Clean up any open resources.
//    super.destroy();
//  }

  /** {@inheritDoc} */
//  @Override
//  protected void process(String lastSourceOffset, int maxBatchSize, BatchMaker batchMaker) throws StageException {
//    // Offsets can vary depending on the data source. Here we use an integer as an example only.
//    long nextSourceOffset = 0;
//    if (lastSourceOffset != null) {
//      nextSourceOffset = Long.parseLong(lastSourceOffset);
//    }
//
//    int numRecords = 0;
//
//    // TODO: As the developer, implement your logic that reads from a data source in this method.
//
//    // Create records and add to batch. Records must have a string id. This can include the source offset
//    // or other metadata to help uniquely identify the record itself.
//    while (numRecords < maxBatchSize) {
//      Record record = getContext().createRecord("some-id::" + nextSourceOffset);
//      Map<String, Field> map = new HashMap<>();
//      map.put("fieldName", Field.create("Some Value"));
//      record.set(Field.create(map));
//      batchMaker.addRecord(record);
//      ++nextSourceOffset;
//      ++numRecords;
//    }
//
//    return String.valueOf(nextSourceOffset);
//  }
  @Override
  protected void process(Record record, SingleLaneBatchMaker batchMaker) throws StageException {
    // TODO: Implement your record processing here, then add to the output batch.

    String line = record.get("/text").getValue().toString();

    LOG.info("Record : {}", line);
    if(line.contains("endoffile")){
      PCF pcf = new PCF();
      LOG.info("Header Records : {}", headerRecords);
      LOG.info("Data Records : {}", dataRecords);
      pcf.setHeaderPCF(processHeader(headerRecords));
      pcf.setDataPCFS(processData(dataRecords));
      //pcfs.add(pcf);
      LOG.info("PCF object : {}", pcf);
      //LOG.info("PCFS object : {}", pcfs);
      dataRecords.clear();
      createRecord(pcf, batchMaker);
      //List<Record> recordList = createRecord(pcfs, batchMaker);
      //BatchMaker batchMaker1 = new Ma
      //recordList.forEach(finalrecord ->{batchMaker.addRecord(finalrecord);});
    }
    else if(line.contains("name")){
      if(!header && data) {
        PCF pcf = new PCF();
        pcf.setHeaderPCF(processHeader(headerRecords));
        pcf.setDataPCFS(processData(dataRecords));
        createRecord(pcf, batchMaker);
        pcfs.add(pcf);
        //dataPCFS.clear();
        LOG.info("PCF object : {}", pcf);
        LOG.info("PCFS object : {}", pcfs);
      }
      headerRecords.add(line);
      LOG.info("Line 1 : {}", line);
      LOG.info("HeaderRecords : {}", headerRecords);
      header = true;
      data = false;
      //dataPCFS.clear();
    }
    else if(line.contains("salary")){
      LOG.info("Reading data : {}", line);
      //processHeader(headerRecords);
      data = true;
      header = false;
    }
    else if(header && !data) {
      LOG.info("Line 2 : {}", line);
      headerRecords.add(line);
    }
    else if(!header && data) {
      dataRecords.add(line);
    }

    LOG.info("Header : {}", header);

    // This example is a no-op
    //batchMaker.addRecord(record);
  }
  public HeaderPCF processHeader(List<String>headerRecords) throws StageException{
    LOG.info("Processing header");
    List<String>headerList = new ArrayList<>();
    headerRecords.forEach(s -> {
      headerList.addAll(Arrays.asList(s.split(",")));
    });
    System.out.println("printing header elements");
    System.out.println(headerList);
    HeaderPCF headerPCF = new HeaderPCF();
    try {
      headerPCF.setName(headerList.get(1));
      headerPCF.setAge(Integer.parseInt(headerList.get(3)));
      headerPCF.setFather(headerList.get(5));
      headerPCF.setFriend(headerList.get(7));
      headerPCF.setFriend_age(Integer.parseInt(headerList.get(9)));
      headerPCF.setAdd(headerList.get(11));
      //System.out.printf("Headr Object", headerPCF);
      LOG.info("Header Object : {}", headerPCF);
      //headerPCF.

    }catch (NumberFormatException exception){
      LOG.error("ERROR", exception);
      headerRecords.clear();
      throw new OnRecordErrorException(Errors.SAMPLE_00, exception.toString());
      //return null;
    }
    headerRecords.clear();
    return headerPCF;
  }

  public List<DataPCF> processData(List<String> dataRecords) throws StageException{
    LOG.info("Processing data");
    List<DataPCF>dataPCFS = new ArrayList<>();
    try {
      dataRecords.forEach(s -> {
        DataPCF dataPCF = new DataPCF();
        String[] dataList = replaceEmptyWithNull(s.split(","));
        System.out.println(dataList);
        dataPCF.setSalary(dataList[0] == null ? null : new BigDecimal(dataList[0]));
        System.out.println("year" + dataList[1]);
        dataPCF.setYear(dataList[1]);
        dataPCF.setEmpid(dataList[2]);
        LOG.info("Data Object : {}", dataPCF);
        dataPCFS.add(dataPCF);


      });
    }catch(NumberFormatException exception){
      LOG.error(": {}",exception);
      System.out.println(exception);
      dataRecords.clear();
      throw new OnRecordErrorException(Errors.SAMPLE_00, exception.toString());
      //throw new OnRecordErrorException(Errors.SAMPLE_01, exception);
      //return new ArrayList<DataPCF>();
    };
    LOG.info("Printing data object : {}", dataPCFS);
    dataRecords.clear();
    return dataPCFS;

  }

  public void createRecord(PCF pcf, SingleLaneBatchMaker batchMaker){
    int i = 1;
    //List<Record> recordList = new ArrayList<>();
    //pcfs.forEach(pcf -> {
        Record headerRecord = getContext().createRecord("header record::" + i);

        System.out.println(pcf.toString());
        if(!pcf.getDataPCFS().isEmpty() && pcf.getHeaderPCF() != null) {
          Map<String, Field> map = new HashMap<>();
          LOG.info("PCF  : {}", pcf.toString());
          Map<String, Field> headerMap = new HashMap<>();
          headerMap.put("name", Field.create(pcf.getHeaderPCF().getName()));
          headerMap.put("age", Field.create(pcf.getHeaderPCF().getAge()));
          headerMap.put("father", Field.create(pcf.getHeaderPCF().getFather()));
          map.put("pcf_header", Field.create(headerMap));
          headerRecord.set(Field.create(map));
          batchMaker.addRecord(headerRecord);

          List<Field> fieldList = new ArrayList<>();
          int j = 1;
          pcf.getDataPCFS().forEach(dataPCF -> {
            Record dataRecord = getContext().createRecord("data record::" + j);
            //LOG.info("Data PCF : {}", dataPCF);
            //LOG.info("Data PCF toString : {}", dataPCF.toString());
            Map<String, Field> pcfDataMap = new HashMap<>();
            Map<String, Field> dataMap = new HashMap<>();
            pcfDataMap.put("salary", Field.create(dataPCF.getSalary()));
            pcfDataMap.put("year", Field.create(dataPCF.getYear()));
            pcfDataMap.put("empid", Field.create(dataPCF.getEmpid()));
            dataMap.put("data", Field.create(pcfDataMap));
            dataRecord.set(Field.create(dataMap));
            LOG.info("output data record : {}", dataRecord);
            batchMaker.addRecord(dataRecord);
            //fieldList.add(Field.create(dataPCF.toString()));
            //pcfDataMap.put("pcf_data", Field.create(dataPCF.toString()))
            //j = j + 1;
          });
        }
        //map.put("pcf_data", Field.create(fieldList));
        //pcfDataMap.put("pcf_data", (List<Field>) Field.create(fieldList));

        //LOG.info("Output Record : {}", headerRecord);

        //recordList.add(record);
    //});
    //return record;
  }

  String [] replaceEmptyWithNull(String[] arr){
    String[] res = new String[arr.length];
    for(int i = 0; i < arr.length; i++){
      if(arr[i].isEmpty())
        res[i] = null;
      else
        res[i] = arr[i];
    }
    return res;
  }

}