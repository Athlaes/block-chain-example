package fr.ul.sid.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilsTest {
    @Test
    void testSha256() {
        String res = StringUtils.applySha256("hello");
        assertEquals("b7c1b6e4cdeb34c03c0fc0e0e4de5b80189d3505470087932d39302f9c99f290", res);
    }

    @Test
    void testDifficultyString() {
        String res = StringUtils.getDifficultyString(3);
        assertEquals("000", res);
    }

}
