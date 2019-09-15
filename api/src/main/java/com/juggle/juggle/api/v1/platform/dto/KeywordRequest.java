package com.juggle.juggle.api.v1.platform.dto;

import java.io.Serializable;

public class KeywordRequest implements Serializable {
    private String keyword;

    public String getKeyword(){
        return keyword;
    }

    public void setKeyword(String keyword){
        this.keyword = keyword;
    }
}
