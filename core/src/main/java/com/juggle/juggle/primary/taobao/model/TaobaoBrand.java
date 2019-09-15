package com.juggle.juggle.primary.taobao.model;

import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.framework.data.json.meta.ExtView;
import com.juggle.juggle.framework.data.json.meta.Formula;
import com.juggle.juggle.framework.data.json.meta.One;
import com.juggle.juggle.primary.taobao.forumal.CalTaobaoBrandExtra;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "taobao_brand")
@Formula(value="extra",calc = CalTaobaoBrandExtra.class)
@ExtView
public class TaobaoBrand extends AuditEntity {
    @NotNull
    @Filtered
    @One(value="category",target = TaobaoCategory.class)
    private Long categoryId;

    private String thumbnail;

    @NotBlank
    private String name;

    private String detail;

    private String storeName;

    private String storeUrl;

    private Integer sort;

    @Filtered
    private boolean enabled;

    public Long getCategoryId(){
        return categoryId;
    }

    public void setCategoryId(Long categoryId){
        this.categoryId = categoryId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
