package com.juggle.juggle.primary.shop.model;

import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "shop_product_comment")
public class ShopProductComment extends LongEntity {
    @Filtered
    private Long memberId;

    @Filtered
    private Long productId;

    private String avatar;

    private String nickName;

    private String content;

    private Integer cStar;

    private String images;

    private Date createdTime;

    public Long getMemberId(){
        return memberId;
    }

    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    public Long getProductId(){
        return productId;
    }

    public void setProductId(Long productId){
        this.productId = productId;
    }

    public String getAvatar(){
        return avatar;
    }

    public void setAvatar(String avatar){
        this.avatar = avatar;
    }

    public String getNickName(){
        return nickName;
    }

    public void setNickName(String nickName){
        this.nickName = nickName;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public Integer getcStar(){
        return cStar;
    }

    public void setcStar(Integer cStar){
        this.cStar = cStar;
    }

    public String getImages(){
        return images;
    }

    public void setImages(String images){
        this.images = images;
    }

    public Date getCreatedTime(){
        return createdTime;
    }

    public void setCreatedTime(Date createdTime){
        this.createdTime = createdTime;
    }
}
