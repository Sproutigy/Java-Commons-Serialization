package com.sproutigy.commons.serialization.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer {
    byte[] serialize(Object object) throws SerializationException;
    void serialize(Object object, OutputStream out) throws SerializationException, IOException;

    <T> T deserialize(byte[] serialized) throws SerializationException;
    <T> T deserialize(byte[] serialized, int offset, int length) throws SerializationException;
    <T> T deserialize(InputStream in) throws SerializationException, IOException;

    <T> T deserialize(Class<T> clazz, byte[] serialized) throws SerializationException, IOException;
    <T> T deserialize(Class<T> clazz, byte[] serialized, int offset, int length) throws SerializationException;
    <T> T deserialize(Class<T> clazz, InputStream in) throws SerializationException, IOException;
}
