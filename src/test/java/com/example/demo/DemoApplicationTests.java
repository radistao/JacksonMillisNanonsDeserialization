package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DemoApplicationTests {

  static final Logger log = LoggerFactory.getLogger(DemoApplicationTests.class);

  static final Instant INSTANT = Instant.parse("2024-08-25T00:00:00.042Z");
  static final Date DATE = Date.from(INSTANT);
  static final DateHolder DATE_HOLDER = new DateHolder(DATE);

  @Autowired
  @Qualifier("objectMapper")
  ObjectMapper objectMapperNanos;

  @Autowired
  @Qualifier("objectMapperMillis")
  ObjectMapper objectMapperMillis;

  String dateSerialized;
  String instantSerializedNanos;
  String instantSerializedMillis;

  @BeforeEach
  void setUp() throws JsonProcessingException {
    dateSerialized = objectMapperNanos.writeValueAsString(DATE_HOLDER);

    InstantHolder instantHolder = new InstantHolder(INSTANT);

    instantSerializedNanos = objectMapperNanos.writeValueAsString(instantHolder);
    instantSerializedMillis = objectMapperMillis.writeValueAsString(instantHolder);
  }

  @Test
  void demo() throws JsonProcessingException {
    log.info("dateSerialized: {}", dateSerialized);
    log.info("instantSerializedNanos: {}", instantSerializedNanos);
    log.info("instantSerializedMillis: {}", instantSerializedMillis);

    long dateTimestamp = DATE_HOLDER.date().getTime();
    long instantEpochMillis = DATE_HOLDER.date().toInstant().toEpochMilli();
    long instantEpochSeconds = DATE_HOLDER.date().toInstant().getEpochSecond();
    Instant parsedTimestampMillis = Instant.ofEpochMilli(dateTimestamp);
    Instant parsedTimestampSecond = Instant.ofEpochSecond(dateTimestamp);
    InstantHolder instantDeserialized = objectMapperNanos.readValue(dateSerialized, InstantHolder.class);
  }

  @Test
  void dateToInstantDeserialization_Enabled_READ_DATE_TIMESTAMPS_AS_NANOSECONDS() throws JsonProcessingException {
    InstantHolder instantDeserialized = objectMapperNanos.readValue(dateSerialized, InstantHolder.class);
    log.info("Instant deserialized NANOS: {}", instantDeserialized);
    assertEquals(INSTANT, instantDeserialized.date());
  }

  @Test
  void dateToInstantDeserialization_Disabled_READ_DATE_TIMESTAMPS_AS_NANOSECONDS() throws JsonProcessingException {
    InstantHolder instantDeserialized = objectMapperMillis.readValue(dateSerialized, InstantHolder.class);
    log.info("Instant deserialized MILLIS: {}", instantDeserialized);
    assertEquals(INSTANT, instantDeserialized.date());
  }

}
