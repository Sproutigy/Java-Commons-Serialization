package com.sproutigy.commons.serialization.standard;

import com.sproutigy.commons.serialization.api.Serialization;
import com.sproutigy.commons.serialization.api.Serializer;
import com.sproutigy.commons.serialization.api.SerializerProvider;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringSerializerTest {

    @Test
    public void testSPIAvailability() {
        Assert.assertEquals("String", Serialization.SPI().byProviderName("String").getName());
    }

    @Test
    public void testSerializationAndDeserializationByteArray() {
        SerializerProvider provider = new StringSerializerProvider();
        Serializer serializer = provider.newSerializer();

        byte[] data = serializer.serialize("ABC");
        assertEquals(3, data.length);
        assertEquals('A', data[0]);
        assertEquals('B', data[1]);
        assertEquals('C', data[2]);

        String deserialized = serializer.deserialize(data);
        assertEquals("ABC", deserialized);
    }

}
