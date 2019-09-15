package com.juggle.juggle.primary.cms.model;

import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="article")
public class Article extends AuditEntity {
    @Filtered
    @NotBlank
    private String code;

    private String thumbnail;

    @Filtered
    @NotBlank
    private String title;

    @Filtered
    @NotBlank
    private String brief;

    private String content;

    private Integer sort;

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getThumbnail(){
        return thumbnail;
    }

    public void setThumbnail(String thumbnail){
        this.thumbnail = thumbnail;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getBrief(){
        return brief;
    }

    public void setBrief(String brief){
        this.brief = brief;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public Integer getSort(){
        return sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }
}
