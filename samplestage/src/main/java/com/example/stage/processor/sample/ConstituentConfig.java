package com.example.stage.processor.sample;

import com.streamsets.pipeline.api.ConfigDef;
import com.streamsets.pipeline.api.FieldSelectorModel;
import com.streamsets.pipeline.api.ValueChooserModel;
import com.streamsets.pipeline.*;

public class ConstituentConfig {
    @ConfigDef(
            required = true,
            type = ConfigDef.Type.STRING,
            defaultValue="",
            label = "Field Name",
            description = "The field in the header of the file.",
            displayPosition = 10,
            group = "CONSTITUENT"
    )
    @FieldSelectorModel(singleValued = true)
    public String fieldName;

    @ConfigDef(
            required = true,
            type = ConfigDef.Type.STRING,
            defaultValue="",
            label = "Field Position",
            description="The field position in the header of the file.",
            displayPosition = 20,
            group = "CONSTITUENT"
    )
    public String fieldPosition;

    @ConfigDef(
            required = true,
            type = ConfigDef.Type.MODEL,
            defaultValue="",
            label = "Field Type",
            description="The field data type.",
            displayPosition = 30,
            group = "CONSTITUENT"
    )
    //@ValueChooserModel(PrimitiveFieldTypeChooserValues.class)
    public String fieldDataTYpe;
}
