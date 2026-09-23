package com.planitautomation.utils;

import java.math.BigDecimal;

/**
 * The application under test renders money as free-form text such as "$10.99",
 * "Total: 116.9" or "21.98". This centralises the parsing so every page object and
 * step definition uses one, well-tested rule for turning that text into a BigDecimal.
 */
public final class CurrencyParser {

    private CurrencyParser() {
    }

    public static BigDecimal parse(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Cannot parse a currency value from empty text.");
        }

        String numeric = text.replaceAll("[^0-9.\\-]", "");

        if (numeric.isBlank()) {
            throw new IllegalArgumentException("Could not parse a currency value out of '" + text + "'.");
        }

        try {
            return new BigDecimal(numeric);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Could not parse a currency value out of '" + text + "'.", e);
        }
    }
}
