package com.juggle.juggle.primary.system.dto;

import java.io.Serializable;

public class TreeSelectItem implements Serializable {
    private Long value;

    private String label;

    private Long parent;

    public Long getValue(){
        return value;
    }

    public void setValue(Long value){
        this.value = value;
    }

    public String getLabel(){
        return label;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public Long getParent(){
        return parent;
    }

    public void setParent(Long parent){
        this.parent = parent;
    }
}
