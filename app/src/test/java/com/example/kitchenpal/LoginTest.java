package com.example.kitchenpal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LoginTest {
    String emailCorrect = "abc@gmail.com";
    String emailNoSymbol = "abcgmail.com";
    String emailWithDot ="abc.name@gmail.com";
    String emailWithHyphen = "abc-name@gmail.com";
    String emailNoCom = "abc@gmail";
    String emailNoUsername = "@gmail.com";

    Methods methods = new Methods();

    @Test
    public void testUsingSimpleRegex() {
        assertTrue(methods.patternMatches(emailCorrect));
        assertTrue(methods.patternMatches(emailWithDot));
        assertTrue(methods.patternMatches(emailWithHyphen));

        assertFalse(methods.patternMatches(emailNoSymbol));
        assertFalse(methods.patternMatches(emailNoCom));
        assertFalse(methods.patternMatches(emailNoUsername));
    }
}
