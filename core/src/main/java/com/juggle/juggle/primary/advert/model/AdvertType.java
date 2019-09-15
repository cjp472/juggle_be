package com.juggle.juggle.primary.advert.model;

import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "advert_type")
public class AdvertType extends AuditEntity {
    @Filtered
    private String code;

    @Filtered
    @NotBlank
    private String name;

    @Filtered
    private String detail;

    @Filtered
    private boolean enabled;

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

    public String getDetail(){
        return detail;
    }

    public void setDetail(String detail){
        this.detail = detail;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }
}
