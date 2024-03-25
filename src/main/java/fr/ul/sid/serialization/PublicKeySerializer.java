package fr.ul.sid.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fr.ul.sid.utils.KeyUtils;

import java.io.IOException;
import java.security.PublicKey;

public class PublicKeySerializer extends JsonSerializer<PublicKey> {
    @Override
    public void serialize(PublicKey publicKey, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String res = KeyUtils.serializePublicKey(publicKey);
        jsonGenerator.writeString(res);
    }
}
