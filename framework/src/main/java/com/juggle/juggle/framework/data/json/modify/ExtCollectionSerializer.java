package com.juggle.juggle.framework.data.json.modify;

import java.io.IOException;
import java.util.Collection;

import com.juggle.juggle.framework.data.json.ExtRetriever;
import com.juggle.juggle.framework.data.json.context.ExtViewContext;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ExtCollectionSerializer 
	extends JsonSerializer
{
	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		Collection col = (Collection)value;
		try {
            ExtRetriever.getInstance().retrieve(col);
        }
        catch (Exception ex) {
        	ex.printStackTrace();
        	throw new RuntimeException(ex);
        }
    	ExtViewContext.get().setInsideCollection(true);
		gen.writeStartArray();
		for (Object obj : col) {
			gen.writeObject(obj);
		}
		gen.writeEndArray();
    	ExtViewContext.get().setInsideCollection(false);
	}
}