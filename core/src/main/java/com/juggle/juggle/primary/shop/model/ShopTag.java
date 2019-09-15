package com.juggle.juggle.primary.shop.model;

import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "shop_tag")
public class ShopTag extends AuditEntity {
    private String code;

    @NotBlank
    @Filtered
    private String name;

    private String detail;

    @NotBlank
    private String theme;

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

    public String getTheme(){
        return theme;
    }

    public void setTheme(String theme){
        this.theme = theme;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }
}
