package com.juggle.juggle.primary.taobao.model;

import com.juggle.juggle.framework.common.validation.meta.State;
import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.primary.Constants;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "taobao_extra")
public class TaobaoExtra extends LongEntity {
    @State(Constants.TAOBAO_SUB.class)
    private String sub;

    private Long subId;

    private String shopPictUrl;

    private String images;

    private Date updatedTime;

    private Date createdTime;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public String getShopPictUrl(){
        return shopPictUrl;
    }

    public void setShopPictUrl(String shopPictUrl){
        this.shopPictUrl = shopPictUrl;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
