package com.jlyang.hadoop.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilsTest {

    @Test
    void isNumeric1() {
        String origin = "1.0";
        assertTrue(StringUtils.isNumeric(origin));
    }

    @Test
    void isNumeric2() {
        String origin = "1.00";
        assertTrue(StringUtils.isNumeric(origin));
    }

    @Test
    void isNumeric3() {
        String origin = "1";
        assertTrue(StringUtils.isNumeric(origin));
    }

    @Test
    void isNumeric4() {
        String origin = "1.";
        assertFalse(StringUtils.isNumeric(origin));
    }

    @Test
    void isNumeric5() {
        String origin = "1.2";
        assertFalse(StringUtils.isNumeric(origin));
    }

    @Test
    void isNumeric6() {
        String origin = ".2";
        assertFalse(StringUtils.isNumeric(origin));
    }

    @Test
    void isNumeric7() {
        String origin = "1..0";
        assertFalse(StringUtils.isNumeric(origin));
    }

    @Test
    void isNumeric8() {
        String origin = "106.0";
        assertTrue(StringUtils.isNumeric(origin));
    }
}