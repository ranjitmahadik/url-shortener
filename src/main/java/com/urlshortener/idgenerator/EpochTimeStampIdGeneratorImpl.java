package com.urlshortener.idgenerator;

public class EpochTimeStampIdGeneratorImpl implements IDGenerator {
    @Override
    public Long generateId() {
        return System.currentTimeMillis();
    }
}
