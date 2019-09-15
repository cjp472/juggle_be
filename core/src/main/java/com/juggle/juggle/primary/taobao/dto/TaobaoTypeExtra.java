package com.juggle.juggle.primary.taobao.dto;

import java.io.Serializable;

public class TaobaoTypeExtra implements Serializable {
    private int brandNum;

    public int getBrandNum(){
        return brandNum;
    }

    public void setBrandNum(int brandNum){
        this.brandNum = brandNum;
    }
}
