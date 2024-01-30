package fr.ul.sid.utils;

import java.security.MessageDigest;

public class StringUtils {

    // Applique Sha256 sur une chaîne de caractères et renvoie le résultat
    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Applique sha256 à notre input,
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer(); // Cette contiendra le hash en hexadécimal
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Renvoie une chaîne de difficulté cible pour la preuve de travail pour créer un hash (ex: difficulté 5 renverra "00000")
    public static String getDifficultyString(int difficulty) {
        return new String(new char[difficulty]).replace('\0', '0');
    }
}
