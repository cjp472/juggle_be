package com.juggle.juggle.primary.cms.dto;

import java.io.Serializable;

public class ResourceExtra implements Serializable {
    private String subtitle;

    private String describe;

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
