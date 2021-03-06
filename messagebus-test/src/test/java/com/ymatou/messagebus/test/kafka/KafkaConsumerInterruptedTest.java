/*
 *
 *  (C) Copyright 2017 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */
package com.ymatou.messagebus.test.kafka;

import org.apache.commons.lang3.RandomUtils;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class KafkaConsumerInterruptedTest {

    private static Logger logger = LoggerFactory.getLogger(KafkaConsumerInterruptedTest.class);

    public static void main(String[] args) throws Exception{
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.16.103.18:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
//        props.put("auto.commit.interval.ms", "1000");
        props.put("heartbeat.interval.ms", "1000");
        props.put("session.timeout.ms", "10000");
        props.put("max.poll.records", "1");
        props.put("max.poll.interval.ms", "30000");
        props.put("request.timeout.ms", "50000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Consumer<String, String> consumer = new KafkaConsumer<>(props);
                consumer.subscribe(Arrays.asList("messagebus.testjava_kafka_hello"));
                MDC.put("logPrefix",Thread.currentThread().getName());
                try {
                    while (true) {

                        try {
                            ConsumerRecords<String, String> records = consumer.poll(500);
                            for (TopicPartition partition : records.partitions()) {
                                logger.info("==========================={} {}",Thread.currentThread().getName(),records.partitions());

                                List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
                                try {
                                    for (ConsumerRecord<String, String> record : partitionRecords) {
                                        logger.info("===========================Recv kafka message:{}", record);

                                        consumer.commitAsync(Collections.singletonMap(partition, new OffsetAndMetadata(
                                                        record.offset() + 1)),
                                                (offsets, exception) -> {
                                                    if (exception != null) {
                                                        logger.error("Failed to commit kafaka offsets:{}. {}", offsets, exception.getMessage(), exception);
                                                    }
                                                });
                                    }
                                } catch (Exception e) {
                                    // 一个Partition消费异常，继续去消费别的Partition
                                    logger.error("Failed to consume kafka message", e);
                                }
                            }
                        } catch (Exception e) {
                            logger.error("exception",e);
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    logger.error("failed when consume message", e);
                } finally {
                    consumer.close();
                }
            }
        },"slowThread");

        thread.start();

        Executors.newSingleThreadScheduledExecutor().schedule(
        () -> {
            try {
                thread.interrupt();
            } catch (Exception e) {
                logger.error("interrupt exception",e);
            }
        },20,TimeUnit.SECONDS);

    }

}
