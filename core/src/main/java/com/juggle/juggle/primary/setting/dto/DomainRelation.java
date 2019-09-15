package com.juggle.juggle.primary.setting.dto;

import java.io.Serializable;
import java.util.List;

public class DomainRelation implements Serializable {
    private Long id;

    private String name;

    private List<DomainRelation> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DomainRelation> getChildren() {
        return children;
    }

    public void setChildren(List<DomainRelation> children) {
        this.children = children;
    }
}
