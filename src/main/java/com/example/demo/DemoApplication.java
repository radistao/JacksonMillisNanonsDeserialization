package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import static com.fasterxml.jackson.databind.DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean(name = "objectMapper")
  ObjectMapper objectMapper(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
    return jackson2ObjectMapperBuilder
        .featuresToEnable(
            READ_DATE_TIMESTAMPS_AS_NANOSECONDS,
            WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
        .build();
  }

  @Bean(name = "objectMapperMillis")
  ObjectMapper objectMapperMillis(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
    return jackson2ObjectMapperBuilder
        .featuresToDisable(
            READ_DATE_TIMESTAMPS_AS_NANOSECONDS,
            WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
        .build();
  }

}
