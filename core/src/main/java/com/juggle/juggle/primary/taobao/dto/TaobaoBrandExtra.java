package com.juggle.juggle.primary.taobao.dto;

import java.io.Serializable;

public class TaobaoBrandExtra implements Serializable {
    private int productNum;

    public int getProductNum(){
        return productNum;
    }

    public void setProductNum(int productNum){
        this.productNum = productNum;
    }
}
