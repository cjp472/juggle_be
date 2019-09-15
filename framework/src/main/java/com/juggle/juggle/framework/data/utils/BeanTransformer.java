package com.juggle.juggle.framework.data.utils;

import com.juggle.juggle.framework.common.errors.ServiceException;
import com.juggle.juggle.framework.common.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

public class BeanTransformer extends AliasedTupleSubsetResultTransformer {
    private final Class resultClass;

    public BeanTransformer(Class resultClass) {
        if (resultClass == null) {
            throw new IllegalArgumentException("resultClass cannot be null");
        } else {
            this.resultClass = resultClass;
        }
    }

    public void apply(Query query) {
        query.unwrap(NativeQuery.class).setResultTransformer(this);
    }

    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }

    public Object transformTuple(Object[] tuple, String[] aliases) {

        ObjectMapper mapper = JsonUtils.createMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        Map map = new HashMap();

        for (int i = 0; i < aliases.length; i++) {
            map.put(aliases[i], tuple[i]);
        }

        try {
            String jsonStr = mapper.writeValueAsString(map);
            return mapper.readValue(jsonStr, this.resultClass);
        }
        catch (Exception ex) {
            throw new ServiceException(ex);
        }

    }
}