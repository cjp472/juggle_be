package com.juggle.juggle.primary.community.dto;

import com.juggle.juggle.primary.taobao.dto.TaobaoPopularDto;

import java.io.Serializable;
import java.util.Date;

public class CommunityPublishDto implements Serializable {
    private String content;

    private String thumbnail;

    private String avatar;

    private String author;

    private TaobaoPopularDto product;

    private Date createdTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public TaobaoPopularDto getProduct(){
        return product;
    }

    public void setProduct(TaobaoPopularDto product){
        this.product = product;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
