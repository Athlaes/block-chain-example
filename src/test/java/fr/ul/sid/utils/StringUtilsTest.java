package fr.ul.sid.utils;

import fr.ul.sid.wallet.Wallet;
import org.junit.jupiter.api.Test;

import java.security.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StringUtilsTest {
    @Test
    void testSha256() {
        String res = StringUtils.applySha256("hello");
        assertEquals("b7c1b6e4cdeb34c03c0fc0e0e4de5b80189d3505470087932d39302f9c99f290", res);
    }

    @Test
    void testSha256WithWallet() throws NoSuchAlgorithmException {
        Wallet wallet = new Wallet();

        assertNotNull(wallet);
        String res = StringUtils.applySha256(wallet);
        assertNotNull(res);
    }



    @Test
    void testDifficultyString() {
        String res = StringUtils.getDifficultyString(3);
        assertEquals("000", res);
    }

}
