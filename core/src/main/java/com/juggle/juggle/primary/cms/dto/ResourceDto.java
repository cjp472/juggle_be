package com.juggle.juggle.primary.cms.dto;

import java.io.Serializable;

public class ResourceDto implements Serializable {
    private String name;

    private String icon;

    private String thumbnail;

    private String protocol;

    private String uri;

    private ResourceExtra extra;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getThumbnail(){
        return thumbnail;
    }

    public void setThumbnail(String thumbnail){
        this.thumbnail = thumbnail;
    }

    public String getProtocol(){
        return protocol;
    }

    public void setProtocol(String protocol){
        this.protocol = protocol;
    }

    public String getUri(){
        return uri;
    }

    public void setUri(String uri){
        this.uri = uri;
    }

    public ResourceExtra getExtra() {
        return extra;
    }

    public void setExtra(ResourceExtra extra) {
        this.extra = extra;
    }
}
