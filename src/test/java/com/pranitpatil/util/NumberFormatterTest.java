package com.pranitpatil.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NumberFormatterTest {

    @Test
    public void testFormatAmountToString() {
        assertEquals("      1,000", NumberFormatter.formatNumberToString(1000));
        assertEquals("         99", NumberFormatter.formatNumberToString(99));
        assertEquals("123,456,789", NumberFormatter.formatNumberToString(123456789));
        assertEquals(" 12,345,678", NumberFormatter.formatNumberToString(12345678));
    }

    @Test
    public void testAddPaddingToNumber() {
        assertEquals("123456", NumberFormatter.addPaddingToNumber(123456));
        assertEquals(" 12345", NumberFormatter.addPaddingToNumber(12345));
        assertEquals("   120", NumberFormatter.addPaddingToNumber(120));
    }
}
