package com.juggle.juggle.primary.setting.model;

import com.juggle.juggle.framework.data.entity.general.AuditEntity;
import com.juggle.juggle.framework.data.filter.meta.Filtered;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "domain")
public class Domain extends AuditEntity {
    private Long parentId;

    @Filtered
    private String name;

    @Filtered
    private boolean enabled;

    private Integer level;

    @Transient
    private List<Domain> children;

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level){
        this.level = level;
    }

    public List<Domain> getChildren(){
        return children;
    }

    public void setChildren(List<Domain> children){
        this.children = children;
    }

    public List<Long> getChains(){
        return chains;
    }

    public void setChains(List<Long> chains){
        this.chains = chains;
    }
}
