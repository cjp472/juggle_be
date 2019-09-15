package com.juggle.juggle.primary.taobao.model;

import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.framework.data.json.meta.ExtView;
import com.juggle.juggle.framework.data.json.meta.Formula;
import com.juggle.juggle.primary.taobao.forumal.CalTaobaoTypeExtra;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "taobao_category")
@Formula(value="extra",calc = CalTaobaoTypeExtra.class)
@ExtView
public class TaobaoCategory extends AuditEntity {
    @Filtered
    private String code;

    @Filtered
    @NotBlank
    private String name;

    private String detail;

    private Integer sort;

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

    public Integer getSort(){
        return sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }
}
