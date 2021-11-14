package com.example.stage.processor.sample;

import com.streamsets.pipeline.api.ChooserValues;
import com.streamsets.pipeline.api.GenerateResourceBundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@GenerateResourceBundle
public abstract class PrimitiveFieldTypeChooserValues implements ChooserValues {
//    public static final String string = "STRING";
//    public static final String integer = "INTEGER";
//    public static final String decimal = "DECIMAL";
    public static final List<String>dataType = Arrays.asList("STRING", "INTEGER", "DECIMAL");

    @Override
    public String getResourceBundle(){
        return getResourceBundle();
    }

    @Override
    public List<String>getValues(){
        return this.dataType;
    }
}
