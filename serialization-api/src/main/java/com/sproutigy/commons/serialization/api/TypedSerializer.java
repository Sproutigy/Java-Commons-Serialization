package com.sproutigy.commons.serialization.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class TypedSerializer<T> {
    private Class<T> serializableClass;
    private Serializer serializer;

    public TypedSerializer(Class<T> serializableClass, Serializer serializer) {
        this.serializableClass = serializableClass;
        this.serializer = serializer;
    }

    public Class<T> getSerializableClass() {
        return serializableClass;
    }

    public Serializer getSerializer() {
        return serializer;
    }

    public byte[] serialize(T object) throws SerializationException {
        return serializer.serialize(object);
    }

    public void serialize(T object, OutputStream out) throws SerializationException, IOException {
        serializer.serialize(object, out);
    }

    public T deserialize(InputStream in) throws SerializationException, IOException {
        return serializer.deserialize(serializableClass, in);
    }

    public T deserialize(byte[] serialized) throws SerializationException {
        return deserialize(serialized, 0, serialized.length);
    }

    public T deserialize(byte[] serialized, int offset, int length) throws SerializationException {
        return serializer.deserialize(serializableClass, serialized, offset, length);
    }
}
