package fr.ul.sid.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class SignUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static byte[] generateSignature(PrivateKey prik, Object toSign) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(prik);
            signature.update(objectMapper.writeValueAsString(toSign).getBytes(StandardCharsets.UTF_8));
            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkSignature(PublicKey pubk, Object toSign, byte[] sign) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(pubk);
            signature.update(objectMapper.writeValueAsString(toSign).getBytes(StandardCharsets.UTF_8));
            return signature.verify(sign);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
