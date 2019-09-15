package com.juggle.juggle.framework.data.entity;

import java.io.Serializable;

public interface Entity<ID extends Serializable> extends Serializable {
    ID getId();
    void setId(ID id);
}
