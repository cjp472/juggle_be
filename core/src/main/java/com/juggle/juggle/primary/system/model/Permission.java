package com.juggle.juggle.primary.system.model;

import com.juggle.juggle.framework.data.entity.general.LongEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "permission")
public class Permission extends LongEntity {
    private Long parentId;

    private String name;

    private String detail;

    private String uri;

    private boolean enabled;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
