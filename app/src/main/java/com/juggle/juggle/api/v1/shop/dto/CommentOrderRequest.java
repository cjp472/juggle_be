package com.juggle.juggle.api.v1.shop.dto;

import java.io.Serializable;

public class CommentOrderRequest implements Serializable {
    private Long id;

    private String content;

    private String images;

    private Integer cStar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getcStar() {
        return cStar;
    }

    public void setcStar(Integer cStar) {
        this.cStar = cStar;
    }
}
