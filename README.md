# Java-Commons-Serialization
Shared API for object to binary representation serialization and deserialization in JVM

## About
Sproutigy Java Commons Serialization standardise different serialization methods and libraries through a unified easy to use API. It allows to change serialization method any time without changing code responsive for calling specific library. It has also ability to lookup for serialization provider by MIME type, file extension or specific serializer name.

**Still under construction, available only as SNAPSHOT!**

## Requirements
Requires Java 7 or later.

## API
API is availble under `com.sproutigy.commons.serialization.api` package.

### Serializer
Serializer interface:
```java
public interface Serializer {
    byte[] serialize(Object object) throws SerializationException;
    void serialize(Object object, OutputStream out) throws SerializationException, IOException;

    <T> T deserialize(byte[] serialized) throws SerializationException;
    <T> T deserialize(byte[] serialized, int offset, int length) throws SerializationException;
    <T> T deserialize(InputStream in) throws SerializationException, IOException;

    <T> T deserialize(Class<T> clazz, byte[] serialized) throws SerializationException, IOException;
    <T> T deserialize(Class<T> clazz, byte[] serialized, int offset, int length) throws SerializationException;
    <T> T deserialize(Class<T> clazz, InputStream in) throws SerializationException, IOException;
}
```

### SerializerProvider
Describes and creates specific implementation serializers:
```java
public interface SerializerProvider {
    Serializer newSerializer();

    String getName();

    String getDefaultType();
    Collection<String> getTypes();

    String getDefaultFileExtension();
    Collection<String> getFileExtensions();
}
```

### Serialization
Aggregates serializer providers:

```java
public final class Serialization implements Iterable<SerializerProvider> {
    Serialization(Iterable<SerializerProvider> providers); //constructor

    SerializerProvider forFileExtension(String fileExtension) throws SerializationException;
    SerializerProvider forType(String type) throws SerializationException;
    SerializerProvider byProviderName(String name) throws SerializationException;
    <T extends SerializerProvider> T byProviderClass(Class<T> providerClass) throws SerializationException;

    static Serialization SPI();
    static Serialization SPI(ClassLoader classLoader);
}
```

### TypedSerializer<T>
Is a wrapper around serializer with predefined serialization class and parent serializer:
```java
    Class<T> getSerializableClass();
    Serializer getSerializer();

    byte[] serialize(T object) throws SerializationException;
    void serialize(T object, OutputStream out) throws SerializationException, IOException;

    T deserialize(InputStream in) throws SerializationException, IOException;
    T deserialize(byte[] serialized) throws SerializationException;
    T deserialize(byte[] serialized, int offset, int length) thorws SerializationException;
```

### SerializationException
`SerializationException` is an unchecked (extends RuntimeException) exception that is thrown on serialization and deserialization errors other than IOException.


## Usage

Some serialization types are considered standard as they are already implemented by JVM and not require additional dependencies.
For other serialization types specific implementation as a dependency need to be added (or just included in classpath).
All implementations compatible with Java's Service Provider Interface (SPI) seen in classpath could be accessed by `Serialization.SPI()`.

### Standard serializers

#### Java serialization
`JavaSerializer` and `JavaSerializerProvider` serialize through Java's ObjectOutputStream and deserializes using ObjectInputStream.

#### String serialization
`StringSerializer` and `StringSerializerProvider` serializes object's toString() result (or just an input String) and creates byte array representation for it using optionally specified charset (UTF-8 by default). Deserializes **always** to a String.


### Examples

#### Automatic lookup for specific serializer by file extension
```java
Serializer serializerJson = Serialization.SPI().forFileExtension("json").newSerializer();
Serializer serializerJava = Serialization.SPI().forFileExtension("ser").newSerializer();
```

#### Automatic lookup for specific serializer by MIME type
```java
Serializer serializerJson = Serialization.SPI().forType("application/json").newSerializer();
Serializer serializerJava = Serialization.SPI().forType("application/java-serialized-object").newSerializer();
```

#### Manual creation of provider and serializer
```java
SerializerProvider provider = new JavaSerializerProvider();
Serializer serializer = provider.newSerializer();
serializer = new JavaSerializer();
```

#### Standard serialization and deserialization
```java
MyClass myObj = ...
Serializer serializer = ...
byte[] data = serializer.serialize(myObj);
MyClass myObjDeserialized = serializer.deserialize(MyClass.class, data);
```

#### TypedSerializer
```java
byte[] data = ...
Serializer serializer = ...
TypedSerializer<MyClass> typedSerializer = new TypedSerializer<>(MyClass.class, serializer);
MyClass myObj = typedSerializer.deserialize(data); //internally calls serializer.deserialize(MyClass.class, data);
```

### Modules

#### Serialization API
```xml
<groupId>com.sproutigy.commons</groupId>
<artifactId>serialization-api</artifactId>
<version>1.0-SNAPSHOT</version>
```
It has no dependencies.

#### JSON by Jackson serialization
```xml
<groupId>com.sproutigy.commons</groupId>
<artifactId>serialization-json-jackson</artifactId>
<version>1.0-SNAPSHOT</version>
```

Depends on following [Jackson](https://github.com/FasterXML/jackson-core) libraries version 2.8.2: `jackson-core`, `jackson-databind`, `jackson-annotations`.

## More
For more information and commercial support visit [Sproutigy](http://www.sproutigy.com/opensource)
