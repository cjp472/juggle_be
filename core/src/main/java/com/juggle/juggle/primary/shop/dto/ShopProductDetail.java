package com.juggle.juggle.primary.shop.dto;

import com.juggle.juggle.primary.shop.model.ShopProductComment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ShopProductDetail implements Serializable {
    private Long id;

    private String name;

    private String brief;

    private List<String> slideList;

    private List<String> detailList;

    private BigDecimal price;

    private BigDecimal actualPrice;

    private Long volume;

    private List<ShopTagDto> tagList;

    private List<ShopProductComment> commentList;

    private List<ShopProductSkuGroup> skuGroups;

    private BigDecimal reward;

    private boolean upgraded;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public List<String> getSlideList() {
        return slideList;
    }

    public void setSlideList(List<String> slideList) {
        this.slideList = slideList;
    }

    public List<String> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<String> detailList) {
        this.detailList = detailList;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public List<ShopTagDto> getTagList() {
        return tagList;
    }

    public void setTagList(List<ShopTagDto> tagList) {
        this.tagList = tagList;
    }

    public List<ShopProductComment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<ShopProductComment> commentList) {
        this.commentList = commentList;
    }

    public List<ShopProductSkuGroup> getSkuGroups(){
        return skuGroups;
    }

    public void setSkuGroups(List<ShopProductSkuGroup> skuGroups){
        this.skuGroups = skuGroups;
    }

    public BigDecimal getReward() {
        return reward;
    }

    public void setReward(BigDecimal reward) {
        this.reward = reward;
    }

    public boolean isUpgraded() {
        return upgraded;
    }

    public void setUpgraded(boolean upgraded) {
        this.upgraded = upgraded;
    }
}
