package com.juggle.juggle.framework.data.entity;

public interface SoftDelete {
    String FIELD = "deleted";


    long getDeleted();

    void setDeleted(long deleted);
}
