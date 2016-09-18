package com.sproutigy.commons.serialization.standard;

import com.sproutigy.commons.serialization.api.Serializer;
import com.sproutigy.commons.serialization.api.impl.AbstractSerializerProvider;

import java.nio.charset.Charset;

public class StringSerializerProvider extends AbstractSerializerProvider {
    private Charset charset;

    public StringSerializerProvider() {
        this(StringSerializer.DEFAULT_CHARSET);
    }

    public StringSerializerProvider(Charset charset) {
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
    public Serializer newSerializer() {
        if (charset.equals(StringSerializer.DEFAULT_CHARSET)) {
            return StringSerializer.INSTANCE_UTF_8;
        }
        return new StringSerializer(charset);
    }

    @Override
    public String getName() {
        return "String";
    }
}
