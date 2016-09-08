package com.sproutigy.commons.serialization.api;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class Serialization implements Iterable<SerializerProvider> {

    private static Map<ClassLoader, Serialization> spiByClassLoader = new ConcurrentHashMap<>();

    private Iterable<SerializerProvider> providers;

    public Serialization(Iterable<SerializerProvider> providers) {
        this.providers = providers;
    }

    @Override
    public Iterator<SerializerProvider> iterator() {
        return providers.iterator();
    }

    public SerializerProvider forFileExtension(String fileExtension) throws SerializationException {
        String ext = fileExtension.toLowerCase();
        Iterator<SerializerProvider> providerIterator = providers.iterator();
        while (providerIterator.hasNext()) {
            SerializerProvider provider = providerIterator.next();
            Collection<String> exts = provider.getFileExtensions();
            if (exts != null && exts.contains(ext)) {
                return provider;
            }
        }

        throw new SerializationException("Could not find serialization provider for file extension: " + fileExtension);
    }

    public SerializerProvider forType(String type) throws SerializationException {
        Iterator<SerializerProvider> providerIterator = providers.iterator();
        while (providerIterator.hasNext()) {
            SerializerProvider provider = providerIterator.next();
            Collection<String> types = provider.getTypes();
            if (types != null && types.contains(type)) {
                return provider;
            }
        }

        throw new SerializationException("Could not find serialization provider for type: " + type);
    }

    public SerializerProvider byProviderName(String name) throws SerializationException {
        Iterator<SerializerProvider> providerIterator = providers.iterator();
        while (providerIterator.hasNext()) {
            SerializerProvider provider = providerIterator.next();
            if (Objects.equals(provider.getName(), name)) {
                return provider;
            }
        }

        throw new SerializationException("Could not find serialization provider by name: " + name);
    }

    @SuppressWarnings("unchecked")
    public <T extends SerializerProvider> T byProviderClass(Class<T> providerClass) throws SerializationException {        Iterator<SerializerProvider> providerIterator = providers.iterator();
        while (providerIterator.hasNext()) {
            SerializerProvider provider = providerIterator.next();
            if (Objects.equals(provider.getClass(), providerClass)) {
                return (T)provider;
            }
        }

        throw new SerializationException("Could not find serialization provider by class: " + providerClass.getName());
    }


    public static Serialization SPI() {
        return SPI(Thread.currentThread().getContextClassLoader());
    }

    public static Serialization SPI(ClassLoader classLoader) {
        Serialization serialization = spiByClassLoader.get(classLoader);
        if (serialization == null) {
            serialization = new Serialization(ServiceLoader.load(SerializerProvider.class, classLoader));
            spiByClassLoader.put(classLoader, serialization);
        }
        return serialization;
    }

}
