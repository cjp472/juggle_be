package com.juggle.juggle.common.data;

import java.io.Serializable;

public class StringRequest implements Serializable {
    private String value;

    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value = value;
    }
}
