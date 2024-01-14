package com.urlshortener.idgenerator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * total allowed bits = 64 bits
 * 41 bits -> time in epoch milli.
 * 10 bits -> 5 -> data center bits, 5 -> machine id bits
 * 12 bits -> sequence bits
 * ------------------------
 * 63 bits total -> 1 reserved sign bit
 */
@Component
@Slf4j
public class SnowFlakeIDGeneratorImpl implements IDGenerator {
    private final static int MILLISECOND_BITS = 41;
    private final static int DATA_CENTER_BITS = 5;
    private final static int MACHINE_ID_BITS = 5;
    private final static int SEQUENCE_BITS = 12;
    private final static int TOTAL_BITS = 64;
    private static final int MAX_SEQUENCE_NUMBER = (1 << SEQUENCE_BITS) - 1;
    private static final int MAX_DATA_CENTER_NUMBER = (1 << DATA_CENTER_BITS) - 1;
    private static final int MAX_MACHINE_ID_NUMBER = (1 << MACHINE_ID_BITS) - 1;
    private static final long MAX_MILLISECOND_NUMBER = (1L << MILLISECOND_BITS) - 1;
    private static final long MAX_NUMBER_LIMIT = (1L << TOTAL_BITS) - 1;

    private long previousMillisecond = System.currentTimeMillis();

    private int nodeId;
    private int dataCenterId;
    private int sequenceNumber = 0;
    private long base = 1705171140710L;

    public SnowFlakeIDGeneratorImpl() {
    }

    public SnowFlakeIDGeneratorImpl(int nodeId, int dataCenterId, long base) {
        this.nodeId = nodeId & MAX_MACHINE_ID_NUMBER;
        this.base = base & MAX_MILLISECOND_NUMBER;
        this.dataCenterId = dataCenterId & MAX_DATA_CENTER_NUMBER;
    }


    private Long generateSnowFlakeId() {
        long currentMillisecond = System.currentTimeMillis();

        if (currentMillisecond < previousMillisecond) throw new RuntimeException("Invalid System clock!");

        if (currentMillisecond == previousMillisecond) {
            synchronized (this) {
                sequenceNumber = (sequenceNumber + 1) & MAX_SEQUENCE_NUMBER;
            }
            if (sequenceNumber == 0) {
                try {
                    Thread.sleep(1);
                    log.info("Waited for 1 ms");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            sequenceNumber = 0;
        }

        previousMillisecond = currentMillisecond;


        Long id = (currentMillisecond - base) << (MACHINE_ID_BITS + DATA_CENTER_BITS + SEQUENCE_BITS)
                | (dataCenterId << (MACHINE_ID_BITS + SEQUENCE_BITS))
                | (nodeId << (SEQUENCE_BITS))
                | sequenceNumber;


        return id;
    }


    @Override
    public Long generateId() {
        return this.generateSnowFlakeId();
    }
}
