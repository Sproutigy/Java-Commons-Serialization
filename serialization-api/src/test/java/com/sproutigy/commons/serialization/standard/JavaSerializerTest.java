package com.sproutigy.commons.serialization.standard;

import com.sproutigy.commons.serialization.api.Serialization;
import com.sproutigy.commons.serialization.api.Serializer;
import com.sproutigy.commons.serialization.api.SerializerProvider;
import com.sproutigy.commons.serialization.standard.JavaSerializerProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.*;

public class JavaSerializerTest {

    @Test
    public void testSPIAvailability() {
        Assert.assertEquals("Java", Serialization.SPI().forFileExtension("ser").getName());
        assertEquals("Java", Serialization.SPI().forType("application/java-serialized-object").getName());
    }

    @Test
    public void testSerializationAndDeserializationByteArray() {
        LinkedHashMap<String, String> testObject = new LinkedHashMap<>();
        testObject.put("hello", "world");

        SerializerProvider provider = new JavaSerializerProvider();
        Serializer serializer = provider.newSerializer();

        byte[] data = serializer.serialize(testObject);
        assertNotNull(data);
        assertNotEquals(0, data.length);

        LinkedHashMap<String, String> testObjectDeserialized = serializer.deserialize(data);
        assertEquals("world", testObjectDeserialized.get("hello"));
    }

}
