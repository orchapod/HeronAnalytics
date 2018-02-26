package heronKafka;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.twitter.heron.streamlet.Context;
import com.twitter.heron.streamlet.Source;

public class HeronKafkaSource {
	/* Usage Example
	 * 
	 * public static vid main(String[] args) throws Exception {
	 * 	Builder builder = Builder.newBuilder();
	 * 
	 *  KafkaConsumer consumer = HeronKafkaConsumer.createConsumer(props, topcis);
	 *  KafkaSource kafkaSource = new KafkaSource(consumer){
	 *  // Overwrite the get() method here so we can specify how we want the incoming Kafka transformed. This avoids us specifying our data types upfront.
	 *  public Collection get() {
	 *  	String nextMessage = null;
	 *  
	 *  	while(nextMessage == null) {
	 *  		final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
	 *				
	 *				consumerRecords.forEach(record -> {
	 *					String newMessage = record.value();
	 *					messageQueue.add(record);
	 *				});
	 *				
	 *				nextMessage = (String) messageQueue.poll();
	 *			}
	 *			return Collections.singletonList(nextMessage);
	 *	}
	 */
	public static class KafkaSource implements Source {
		public ConcurrentLinkedQueue messageQueue = new ConcurrentLinkedQueue<>();
		private KafkaConsumer consumer;
		@Override
		public void cleanup() {
			// TODO Auto-generated method stub
		}

		@Override
		public Collection get() {
			return Collections.singletonList(messageQueue.poll());
		}

		@Override
		public void setup(Context context) {
		}
		
		public KafkaSource(KafkaConsumer consumer) {
			this.consumer = consumer;
		}
		
	}
}