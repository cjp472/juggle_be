package com.juggle.juggle.common.data;

import java.io.Serializable;

public class TaobaoDetailRequest implements Serializable {
    private Long id;

    private String sub;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
