package com.juggle.juggle.framework.data.entity.uuid;

import com.juggle.juggle.framework.common.utils.UuidUtils;

import javax.persistence.PrePersist;

public class UuidEntityListener {
    @PrePersist
    public void prePersist(Object object) {
        UuidEntity entity = (UuidEntity)object;
        if (null == entity.getUuid()) {
            entity.setUuid(UuidUtils.getUUID());
        }
    }
}
