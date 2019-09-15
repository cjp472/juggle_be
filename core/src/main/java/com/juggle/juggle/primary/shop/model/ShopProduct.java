package com.juggle.juggle.primary.shop.model;

import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.framework.data.json.meta.ExtView;
import com.juggle.juggle.framework.data.json.meta.Formula;
import com.juggle.juggle.framework.data.json.meta.One;
import com.juggle.juggle.primary.shop.forumal.CalShopProductChains;
import com.juggle.juggle.primary.shop.forumal.CalShopTagsText;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
@Table(name = "shop_product")
@Formula(value="chains",calc = CalShopProductChains.class)
@Formula(value = "tagsText",calc = CalShopTagsText.class)
@ExtView
public class ShopProduct extends AuditEntity {
    @Filtered
    @One(value = "type",target = ShopType.class)
    private Long typeId;

    @NotBlank
    @Filtered
    private String name;

    @NotBlank
    private String brief;

    private String thumbnail;

    private String slides;

    private String details;

    private BigDecimal price;

    private BigDecimal actualPrice;

    private Long volume;

    private String tags;

    private BigDecimal reward;

    @Filtered
    private boolean recommend;

    @Filtered
    private boolean upgraded;

    @Filtered
    private boolean enabled;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief(){
        return brief;
    }

    public void setBrief(String brief){
        this.brief = brief;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSlides(){
        return slides;
    }

    public void setSlides(String slides){
        this.slides = slides;
    }

    public String getDetails(){
        return details;
    }

    public void setDetails(String details){
        this.details = details;
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

    public String getTags(){
        return tags;
    }

    public void setTags(String tags){
        this.tags = tags;
    }

    public BigDecimal getReward(){
        return reward;
    }

    public void setReward(BigDecimal reward){
        this.reward = reward;
    }

    public boolean isRecommend(){
        return recommend;
    }

    public void setRecommend(boolean recommend){
        this.recommend = recommend;
    }

    public boolean isUpgraded(){
        return upgraded;
    }

    public void setUpgraded(boolean upgraded){
        this.upgraded = upgraded;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
