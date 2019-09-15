package com.juggle.juggle.primary.taobao.model;

import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "taobao_type")
public class TaobaoType extends AuditEntity {
    private Long parentId;

    private String code;

    @Filtered
    @NotBlank
    private String name;

    private String detail;

    private String thumbnail;

    private Integer sort;

    private String cat;

    @Filtered
    private boolean enabled;

    @Transient
    private List<TaobaoType> children;

    @Transient
    private List<Long> chains;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCat(){
        return cat;
    }

    public void setCat(String cat){
        this.cat = cat;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<TaobaoType> getChildren(){
        return children;
    }

    public void setChildren(List<TaobaoType> children){
        this.children = children;
    }

    public List<Long> getChains(){
        return chains;
    }

    public void setChains(List<Long> chains){
        this.chains = chains;
    }
}
