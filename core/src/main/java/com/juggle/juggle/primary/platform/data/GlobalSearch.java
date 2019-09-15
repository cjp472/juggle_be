package com.juggle.juggle.primary.platform.data;

import java.io.Serializable;

public class GlobalSearch implements Serializable {
    private String type;

    private String field;

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getField(){
        return field;
    }

    public void setField(String field){
        this.field = field;
    }
}
