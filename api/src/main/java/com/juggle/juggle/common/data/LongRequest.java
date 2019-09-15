package com.juggle.juggle.common.data;

import java.io.Serializable;

public class LongRequest implements Serializable {
    private Long id;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
}
