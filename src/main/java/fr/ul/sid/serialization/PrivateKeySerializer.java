package fr.ul.sid.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fr.ul.sid.utils.KeyUtils;

import java.io.IOException;
import java.security.PrivateKey;

public class PrivateKeySerializer extends JsonSerializer<PrivateKey> {
    @Override
    public void serialize(PrivateKey privateKey, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String key = KeyUtils.serializePrivateKey(privateKey);
        jsonGenerator.writeString(key);
    }
}
