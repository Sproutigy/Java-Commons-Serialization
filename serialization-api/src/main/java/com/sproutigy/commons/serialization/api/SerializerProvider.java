package com.sproutigy.commons.serialization.api;

import java.util.Collection;

public interface SerializerProvider {
    Serializer newSerializer();

    String getName();

    String getDefaultType();
    Collection<String> getTypes();

    String getDefaultFileExtension();
    Collection<String> getFileExtensions();
}
