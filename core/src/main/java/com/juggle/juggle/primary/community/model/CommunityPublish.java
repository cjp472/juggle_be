package com.juggle.juggle.primary.community.model;

import com.juggle.juggle.common.forumal.CalCreater;
import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;
import com.juggle.juggle.framework.data.json.meta.ExtView;
import com.juggle.juggle.framework.data.json.meta.Formula;
import com.juggle.juggle.framework.data.json.meta.One;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "community_publish")
@Formula(value="creater",calc = CalCreater.class)
@ExtView
public class CommunityPublish extends AuditEntity {
    @NotNull
    @Filtered
    @One(value = "community",target = Community.class)
    private Long communityId;

    private String thumbnail;

    @Filtered
    @NotBlank
    private String title;

    @Filtered
    @NotBlank
    private String content;

    private Long popularId;

    private Integer shareCount = 0;

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getThumbnail(){
        return thumbnail;
    }

    public void setThumbnail(String thumbnail){
        this.thumbnail = thumbnail;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPopularId(){
        return popularId;
    }

    public void setPopularId(Long popularId){
        this.popularId = popularId;
    }

    public Integer getShareCount(){
        return shareCount;
    }

    public void setShareCount(Integer shareCount){
        this.shareCount = shareCount;
    }
}
