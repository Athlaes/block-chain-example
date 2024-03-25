package fr.ul.sid.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import fr.ul.sid.utils.KeyUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

public class PublicKeyDeserializer extends JsonDeserializer<PublicKey> {
    @Override
    public PublicKey deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            return KeyUtils.deserializePublicKey(jsonParser.getValueAsString());
        } catch (NoSuchAlgorithmException |InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
