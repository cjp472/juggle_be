package com.juggle.juggle.primary.shop.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ShopProductRecommend implements Serializable {
    private Long id;

    private String thumbnail;

    private String name;

    private BigDecimal price;

    private BigDecimal actualPrice;

    private Long volume;

    private boolean upgraded;

    private List<ShopTagDto> tags;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getThumbnail(){
        return thumbnail;
    }

    public void setThumbnail(String thumbnail){
        this.thumbnail = thumbnail;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public BigDecimal getPrice(){
        return price;
    }

    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public BigDecimal getActualPrice(){
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice){
        this.actualPrice = actualPrice;
    }

    public Long getVolume(){
        return volume;
    }

    public void setVolume(Long volume){
        this.volume = volume;
    }

    public boolean isUpgraded(){
        return upgraded;
    }

    public void setUpgraded(boolean upgraded){
        this.upgraded = upgraded;
    }

    public List<ShopTagDto> getTags(){
        return tags;
    }

    public void setTags(List<ShopTagDto> tags){
        this.tags = tags;
    }
}
