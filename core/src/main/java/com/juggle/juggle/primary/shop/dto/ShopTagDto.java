package com.juggle.juggle.primary.shop.dto;

import java.io.Serializable;

public class ShopTagDto implements Serializable {
    private String name;

    private String theme;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getTheme(){
        return theme;
    }

    public void setTheme(String theme){
        this.theme = theme;
    }
}
