package com.sproutigy.commons.serialization.api.impl;

import com.sproutigy.commons.serialization.api.SerializationException;
import com.sproutigy.commons.serialization.api.Serializer;

import java.io.*;

public abstract class AbstractSerializer implements Serializer {

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            serialize(object, baos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return baos.toByteArray();
    }

    @Override
    public void serialize(Object object, OutputStream out) throws SerializationException, IOException {
        out.write(serialize(object));
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] serialized) throws SerializationException {
        return deserialize(clazz, serialized, 0, serialized.length);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] serialized, int offset, int length) throws SerializationException {
        try {
            return deserialize(clazz, new ByteArrayInputStream(serialized, offset, length));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(Class<T> clazz, InputStream in) throws SerializationException, IOException {
        ByteArrayOutputStream builder = new ByteArrayOutputStream(512);

        int read;
        byte[] data = new byte[16384];

        while ((read = in.read(data, 0, data.length)) != -1) {
            builder.write(data, 0, read);
        }

        builder.flush();

        return deserialize(clazz, builder.toByteArray());
    }

    @Override
    public <T> T deserialize(byte[] serialized) {
        return deserialize(serialized, 0, serialized.length);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] serialized, int offset, int length) throws SerializationException {
        return (T) deserialize(Object.class, serialized, offset, length);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(InputStream in) throws SerializationException, IOException {
        return (T) deserialize(Object.class, in);
    }
}
