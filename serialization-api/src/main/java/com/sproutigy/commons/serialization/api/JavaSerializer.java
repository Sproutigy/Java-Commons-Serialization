package com.sproutigy.commons.serialization.api;

import com.sproutigy.commons.serialization.api.impl.AbstractSerializer;

import java.io.*;

public class JavaSerializer extends AbstractSerializer {
    @Override
    public void serialize(Object object, OutputStream out) throws SerializationException, IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(out)) {
            objectOutputStream.writeObject(object);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(Class<T> clazz, InputStream in) throws SerializationException, IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(in)) {
            try {
                return (T) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                throw new SerializationException(e);
            }
        }
    }
}
