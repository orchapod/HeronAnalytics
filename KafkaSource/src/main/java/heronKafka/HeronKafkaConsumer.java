package heronKafka;

import java.util.Collections;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class HeronKafkaConsumer {
	
	 private static final Logger LOG = Logger.getLogger(KafkaConsumer.class.getName());
	
	 public static KafkaConsumer createConsumer(Properties props, String topics){
		final KafkaConsumer consumer = new KafkaConsumer(props);
		
		consumer.subscribe(Collections.singletonList(topics));
		
		return consumer;
	}
}