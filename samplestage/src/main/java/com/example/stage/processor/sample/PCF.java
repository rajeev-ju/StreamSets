package com.example.stage.processor.sample;

import com.streamsets.pipeline.api.Field;

import java.util.List;

public class PCF {
    private HeaderPCF headerPCF;
    private List<DataPCF>dataPCFS;

    public HeaderPCF getHeaderPCF() {
        return headerPCF;
    }

    public void setHeaderPCF(HeaderPCF headerPCF) {
        this.headerPCF = headerPCF;
    }

    public List<DataPCF> getDataPCFS() {
        return dataPCFS;
    }

    public void setDataPCFS(List<DataPCF> dataPCFS) {
        this.dataPCFS = dataPCFS;
    }

    @Override
    public String toString() {
        return "PCF{" +
                "headerPCF=" + headerPCF +
                ", dataPCFS=" + dataPCFS +
                '}';
    }
}
