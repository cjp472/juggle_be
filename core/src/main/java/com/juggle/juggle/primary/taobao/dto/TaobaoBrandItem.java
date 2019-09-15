package com.juggle.juggle.primary.taobao.dto;

import java.io.Serializable;

public class TaobaoBrandItem implements Serializable {
    private Long id;

    private String thumbnail;

    private String name;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getThumbnail(){
        return thumbnail;
    }

    public void setThumbnail(String thumbnail){
        this.thumbnail = thumbnail;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
