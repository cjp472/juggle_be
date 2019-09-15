package com.juggle.juggle.primary.app.model;

import com.juggle.juggle.framework.data.entity.general.LongEntity;
import com.juggle.juggle.framework.data.json.meta.One;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "invite_record")
public class InviteRecord extends LongEntity {
    @One(value = "member",target = Member.class)
    private Long memberId;

    @One(value = "invitee",target = Member.class)
    private Long beInvitedId;

    private boolean handle;

    private Date updatedTime;

    private Date createdTime;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getBeInvitedId() {
        return beInvitedId;
    }

    public void setBeInvitedId(Long beInvitedId) {
        this.beInvitedId = beInvitedId;
    }

    public boolean isHandle() {
        return handle;
    }

    public void setHandle(boolean handle) {
        this.handle = handle;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
