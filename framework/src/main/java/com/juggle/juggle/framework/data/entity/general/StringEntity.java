package com.juggle.juggle.framework.data.entity.general;

import com.juggle.juggle.framework.data.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class StringEntity extends BaseEntity<String> {

    @Id
    @Column(name = "ID",updatable=false)
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int compareTo(BaseEntity o) {
        StringEntity other = (StringEntity) o;
        return this.getId().compareTo(other.getId());
    }
}
