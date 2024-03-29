package com.juggle.juggle.framework.data.entity.guid;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDHexGenerator;

import java.io.Serializable;

public class DualGenerator extends UUIDHexGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if(object instanceof IdAssignable) {
            IdAssignable identifiable = (IdAssignable) object;
            Serializable id = identifiable.getId();
            if(id != null) {
                return id;
            }
        }
        return new StringBuffer().append(this.format(this.getHiTime()))
                .append(this.format(this.getLoTime()))
                .append(this.format(this.getCount()) + this.format(this.getIP()))
                .append(this.format(this.getJVM())).toString();
    }
}
