package com.juggle.juggle.primary.business.model;

import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.primary.Constants;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "keyword")
public class Keyword extends AuditEntity {
    @NotBlank
    @Filtered
    @State(Constants.KEYWORD_TYPE.class)
    private String type;

    @Filtered
    @NotBlank
    private String word;

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getWord(){
        return word;
    }

    public void setWord(String word){
        this.word = word;
    }
}
