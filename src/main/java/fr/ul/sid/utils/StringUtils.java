package fr.ul.sid.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.SerializationUtils;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class StringUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    public static String applySha256(Object object) {
        try {
            // Todo : need to change the way it hash so I can hash PublicKeys
            String json = objectMapper.writeValueAsString(object);
            return DigestUtils.sha3_256Hex(json.getBytes(StandardCharsets.UTF_8));
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDifficultyString(int difficulty) {
        return new String(new char[difficulty]).replace('\0', '0');
    }
}
