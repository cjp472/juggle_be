package com.juggle.juggle.primary.taobao.dto;

import java.io.Serializable;

public class TaobaoSummary implements Serializable {
    private long taobaoTypeNum;

    private long taobaoBrandNum;

    private long taobaoProductNum;

    public long getTaobaoTypeNum(){
        return taobaoTypeNum;
    }

    public void setTaobaoTypeNum(long taobaoTypeNum){
        this.taobaoTypeNum = taobaoTypeNum;
    }

    public long getTaobaoBrandNum(){
        return taobaoBrandNum;
    }

    public void setTaobaoBrandNum(long taobaoBrandNum){
        this.taobaoBrandNum = taobaoBrandNum;
    }

    public long getTaobaoProductNum(){
        return taobaoProductNum;
    }

    public void setTaobaoProductNum(long taobaoProductNum){
        this.taobaoProductNum = taobaoProductNum;
    }
}
