package com.sproutigy.commons.serialization.standard;

import com.sproutigy.commons.serialization.api.SerializationException;
import com.sproutigy.commons.serialization.api.impl.AbstractSerializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringSerializer extends AbstractSerializer {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private Charset charset;

    public StringSerializer() {
        this(DEFAULT_CHARSET);
    }

    public StringSerializer(Charset charset) {
        setCharset(charset);
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset == null");
        }
        this.charset = charset;
    }

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        if (object == null) {
            return new byte[0];
        }

        String s = object.toString();
        return s.getBytes(charset);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(Class<T> clazz, byte[] serialized, int offset, int length) throws SerializationException {
        try {
            if (length == 0) {
                return (T)"";
            }

            return (T)new String(serialized, offset, length, charset);
        } catch(Exception e) {
            throw new SerializationException(e);
        }
    }
}
