//package com.practice.security.configs;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.TopicBuilder;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.ContainerProperties;
//import org.springframework.kafka.support.Acknowledgment;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Configuration
//public class KafkaTopicConfig {
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "myId");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 1000);
//        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5);
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // Tắt auto-commit
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//            new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        factory.setBatchListener(true);
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL); // Optional
//        return factory;
//    }
//
//    @Bean
//    public NewTopic topic() {
//        return TopicBuilder.name("topic1")
//            .partitions(10)
//            .replicas(1)
//            .build();
//    }
//
//    @KafkaListener(id = "myId", topics = "topic1")
//    public void listen(List<String> ins, Acknowledgment acknowledgment) throws InterruptedException {
//        System.out.println(Arrays.toString(ins.toArray()));
//    }
//}