package com.sproutigy.commons.serialization.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sproutigy.commons.serialization.api.Serializer;
import com.sproutigy.commons.serialization.api.impl.AbstractSerializerProvider;

import java.util.Collection;
import java.util.Collections;

public class JsonJacksonSerializerProvider extends AbstractSerializerProvider {
    private ObjectMapper objectMapper;

    public JsonJacksonSerializerProvider() {
        objectMapper = new ObjectMapper();
    }

    public JsonJacksonSerializerProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Serializer newSerializer() {
        return new JsonJacksonSerializer(objectMapper.copy());
    }

    @Override
    public Collection<String> getTypes() {
        return Collections.singleton("application/json");
    }

    @Override
    public Collection<String> getFileExtensions() {
        return Collections.singleton("json");
    }

    @Override
    public String getName() {
        return "JSON-Jackson";
    }
}
