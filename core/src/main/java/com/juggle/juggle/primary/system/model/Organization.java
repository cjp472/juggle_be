package com.juggle.juggle.primary.system.model;

import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "organization")
public class Organization extends AuditEntity {
    private Long parentId;

    @Filtered
    private String name;

    @Filtered
    private String detail;

    @Filtered
    private boolean enabled;

    @Transient
    private List<Organization> children;

    @Transient
    private List<Long> chains;

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Long> getChains(){
        return chains;
    }

    public void setChains(List<Long> chains){
        this.chains = chains;
    }

    public List<Organization> getChildren(){
        return children;
    }

    public void setChildren(List<Organization> children){
        this.children = children;
    }
}
