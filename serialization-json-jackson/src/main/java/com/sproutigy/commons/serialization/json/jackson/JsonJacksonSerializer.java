package com.sproutigy.commons.serialization.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sproutigy.commons.serialization.api.SerializationException;
import com.sproutigy.commons.serialization.api.impl.AbstractSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JsonJacksonSerializer extends AbstractSerializer {
    private ObjectMapper objectMapper;

    public JsonJacksonSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public void serialize(Object object, OutputStream out) throws SerializationException, IOException {
        try {
            objectMapper.writeValue(out, object);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] serialized, int offset, int length) throws SerializationException {
        try {
            return objectMapper.readValue(serialized, offset, length, clazz);
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public <T> T deserialize(Class<T> clazz, InputStream in) throws SerializationException, IOException {
        try {
            return objectMapper.readValue(in, clazz);
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }
}
