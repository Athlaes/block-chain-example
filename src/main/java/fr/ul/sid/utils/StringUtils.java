package fr.ul.sid.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;

public class StringUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static String applySha256(Object object) {
        try {
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
