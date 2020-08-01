package com.example.chapter4;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class FirstAppProducerTest {

    FirstAppProducer target = new FirstAppProducer();
    Producer<Integer, String> producer;
    
    @Before
    public void setUp() {
        producer = new MockProducer<>(true,
                new IntegerSerializer(), new StringSerializer());
        
        target.setProducer(producer);
    }
    
    @Test
    public void testProducer() throws IOException {

        target.send();

        List<ProducerRecord<Integer, String>> history = ((MockProducer<Integer, String>) producer).history();

        List<ProducerRecord<Integer, String>> expected = Arrays.asList(
                new ProducerRecord<Integer, String>(FirstAppProducer.TOPIC_NAME, 1, "1"),
                new ProducerRecord<Integer, String>(FirstAppProducer.TOPIC_NAME, 2, "2"),
                new ProducerRecord<Integer, String>(FirstAppProducer.TOPIC_NAME, 3, "3"),
                new ProducerRecord<Integer, String>(FirstAppProducer.TOPIC_NAME, 4, "4"),
                new ProducerRecord<Integer, String>(FirstAppProducer.TOPIC_NAME, 5, "5"));

        Assert.assertEquals("Sent didn't match expected", expected, history);
    }
}
