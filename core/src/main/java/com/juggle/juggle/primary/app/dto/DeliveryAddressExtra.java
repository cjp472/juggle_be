package com.juggle.juggle.primary.app.dto;

import java.io.Serializable;

public class DeliveryAddressExtra implements Serializable {
    private String area;

    public String getArea(){
        return area;
    }

    public void setArea(String area){
        this.area = area;
    }
}
