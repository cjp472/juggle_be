package com.juggle.juggle.primary.community.dto;

import java.io.Serializable;

public class CommunityExtra implements Serializable {
    private int todayPublish;

    public int getTodayPublish(){
        return todayPublish;
    }

    public void setTodayPublish(int todayPublish){
        this.todayPublish = todayPublish;
    }

}
