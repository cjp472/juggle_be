package com.juggle.juggle.primary.taobao.dto;

import java.io.Serializable;
import java.util.List;

public class TaobaoAlbum implements Serializable {
    private List<String> images;

    public List<String> getImages(){
        return images;
    }

    public void setImages(List<String> images){
        this.images = images;
    }
}
