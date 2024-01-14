package com.urlshortener.strategies;

import com.urlshortener.idgenerator.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseEncoderStrategyImpl implements IUrlShortenerStrategies {
    @Autowired
    private IDGenerator idGenerator;
    private final static Integer BASE = 62;

    @Override
    public String shortenUrl() {
        Long id = idGenerator.generateId();
        return convertToBase62(id);
    }

    /**
     * 0 - 9 => 10
     * A - Z => 26
     * a - z => 26
     * ------------
     * 62
     */
    public String convertToBase62(long decimalNumber) {
        if (decimalNumber == 0) {
            return "0";
        }

        StringBuilder encodedString = new StringBuilder();

        while (decimalNumber > 0) {
            long remainder = decimalNumber % BASE;
            char base64Char = getBase64Char(remainder);
            encodedString.insert(0, base64Char);
            decimalNumber /= BASE;
        }

        return encodedString.toString();
    }

    private char getBase64Char(long remainder) {
        if (remainder < 26) {
            // A-Z
            return (char) ('A' + remainder);
        } else if (remainder < 52) {
            // a-z
            return (char) ('a' + remainder - 26);
        } else {
            // 0-9
            return (char) ('0' + remainder - 52);
        }
    }
}
