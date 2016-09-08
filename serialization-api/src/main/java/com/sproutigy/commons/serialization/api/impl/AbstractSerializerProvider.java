package com.sproutigy.commons.serialization.api.impl;

import com.sproutigy.commons.serialization.api.SerializerProvider;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public abstract class AbstractSerializerProvider implements SerializerProvider {
    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public Collection<String> getTypes() {
        return Collections.emptyList();
    }

    @Override
    public Collection<String> getFileExtensions() {
        return Collections.emptyList();
    }

    @Override
    public String getDefaultType() {
        Iterator<String> iterator = getTypes().iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return "";
    }

    @Override
    public String getDefaultFileExtension() {
        Iterator<String> iterator = getFileExtensions().iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return "";
    }
}
