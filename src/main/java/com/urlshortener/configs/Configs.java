package com.urlshortener.configs;

import com.urlshortener.idgenerator.IDGenerator;
import com.urlshortener.idgenerator.SnowFlakeIDGeneratorImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configs {

    @Value("${snowflake.machineId}")
    private Integer nodeId;

    @Value("${snowflake.dataCenterId}")
    private Integer dataCenterId;
    @Value("${snowflake.baseEpoch}")
    private Long baseEpochMillisecond;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public IDGenerator idGenerator() {
        if (baseEpochMillisecond == null) baseEpochMillisecond = 0L;
        return new SnowFlakeIDGeneratorImpl(nodeId, dataCenterId, baseEpochMillisecond);
    }
}
