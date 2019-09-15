package com.juggle.juggle.primary.setting.model;

import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.primary.Constants;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "dictionary")
public class Dictionary extends AuditEntity {
    @Filtered
    @NotBlank
    @State(Constants.DICTIONARY_TYPE.class)
    private String type;

    @Filtered
    @NotBlank
    private String dictKey;

    @NotBlank
    private String dictValue;

    @Filtered
    @NotBlank
    private String detail;

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getDictKey(){
        return dictKey;
    }

    public void setDictKey(String dictKey){
        this.dictKey = dictKey;
    }

    public String getDictValue(){
        return dictValue;
    }

    public void setDictValue(String dictValue){
        this.dictValue = dictValue;
    }

    public String getDetail(){
        return detail;
    }

    public void setDetail(String detail){
        this.detail = detail;
    }
}
