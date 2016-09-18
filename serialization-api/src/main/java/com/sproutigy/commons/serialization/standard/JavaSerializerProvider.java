package com.sproutigy.commons.serialization.standard;

import com.sproutigy.commons.serialization.api.Serializer;
import com.sproutigy.commons.serialization.api.impl.AbstractSerializerProvider;

import java.util.Collection;
import java.util.Collections;

public class JavaSerializerProvider extends AbstractSerializerProvider {
    public static final JavaSerializerProvider INSTANCE = new JavaSerializerProvider();

    public Serializer newSerializer() {
        return JavaSerializer.INSTACE;
    }

    @Override
    public Collection<String> getTypes() {
        return Collections.singleton("application/java-serialized-object");
    }

    @Override
    public Collection<String> getFileExtensions() {
        return Collections.singleton("ser");
    }

    @Override
    public String getName() {
        return "Java";
    }
}
