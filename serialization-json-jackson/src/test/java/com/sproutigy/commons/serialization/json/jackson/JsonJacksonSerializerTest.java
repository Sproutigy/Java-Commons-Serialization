package com.sproutigy.commons.serialization.json.jackson;

import com.sproutigy.commons.serialization.api.Serialization;
import com.sproutigy.commons.serialization.api.Serializer;
import com.sproutigy.commons.serialization.api.SerializerProvider;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JsonJacksonSerializerTest {
    private SerializerProvider provider = new JsonJacksonSerializerProvider();
    private Serializer serializer = provider.newSerializer();

    @Test
    public void testSPIAvailability() {
        assertEquals("JSON-Jackson", Serialization.SPI().forFileExtension("json").getName());
        assertEquals("JSON-Jackson", Serialization.SPI().forType("application/json").getName());
    }

    @Test
    public void testSerializationToByteArray() throws UnsupportedEncodingException {
        LinkedHashMap<String, String> testObject = new LinkedHashMap<>();
        testObject.put("hello", "world");

        byte[] data = serializer.serialize(testObject);
        assertNotNull(data);
        assertNotEquals(0, data.length);
        assertEquals("{\"hello\":\"world\"}", new String(data, "UTF-8"));
    }

    @Test
    public void testDeserializationFromByteArray() throws UnsupportedEncodingException {
        String json = "{\"hello\":\"world\"}";

        Map<String, String> testObjectDeserialized = serializer.deserialize(json.getBytes("UTF-8"));
        assertEquals("world", testObjectDeserialized.get("hello"));
    }
}
