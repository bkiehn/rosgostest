package ru.rgs.csvparser.configuration;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.rgs.csvparser.feign.CustomErrorDecoder;

@Configuration
public class ClientConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return  new CustomErrorDecoder();
    }
}

