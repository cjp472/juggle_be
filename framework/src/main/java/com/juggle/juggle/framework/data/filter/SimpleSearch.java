package com.juggle.juggle.framework.data.filter;

import java.io.Serializable;
import java.util.List;

public class SimpleSearch implements Serializable {
    List<ValueFilter> filters;

    public List<ValueFilter> getFilters(){
        return filters;
    }

    public void setFilters(List<ValueFilter> filters){
        this.filters = filters;
    }
}
