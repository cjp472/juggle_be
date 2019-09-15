package com.juggle.juggle.primary.cms.model;

import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.primary.Constants;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "resource")
public class Resource extends AuditEntity {
    @Filtered
    @NotBlank
    private String code;

    @Filtered
    private String name;

    private String thumbnail;

    private String icon;

    @Filtered
    @State(Constants.RESOURCE_PROTOCOL.class)
    private String protocol;

    @Filtered
    private String uri;

    private String extra;

    private Integer sort;

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getThumbnail(){
        return thumbnail;
    }

    public void setThumbnail(String thumbnail){
        this.thumbnail = thumbnail;
    }

    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon = icon;
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

    public String getExtra(){
        return extra;
    }

    public void setExtra(String extra){
        this.extra = extra;
    }

    public Integer getSort(){
        return sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }
}
