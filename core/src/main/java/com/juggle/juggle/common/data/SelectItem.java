package com.juggle.juggle.common.data;

import java.io.Serializable;

public class SelectItem implements Serializable {
    private String label;

    private Object value;

    public SelectItem() {
    }

    public SelectItem(String label, Object value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel(){
        return label;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public Object getValue(){
        return value;
    }

    public void setValue(Object value){
        this.value = value;
    }
}
