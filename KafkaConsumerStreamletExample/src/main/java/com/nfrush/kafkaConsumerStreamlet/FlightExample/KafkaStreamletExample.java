package com.nfrush.kafkaConsumerStreamlet.FlightExample;

import com.google.gson.Gson;
import com.nfrush.kafkaConsumerStreamlet.kafka.StreamletKafkaConsumer;
import com.nfrush.kafkaConsumerStreamlet.models.FlightData;
import com.twitter.heron.streamlet.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public final class KafkaStreamletExample {
    private static final Logger LOG = Logger.getLogger(KafkaStreamletExample.class.getName());
    private static final float CPU = 2.0f;
    private static final long GIGABYTES_OF_RAM = 6;
    private static final int NUM_CONTAINERS = 2;
    private static final Gson gson = new Gson();
    
    private static class KafkaSource implements Source<FlightData> {
         private ConcurrentLinkedQueue<FlightData> messageQueue = new ConcurrentLinkedQueue<>();
         private Consumer<String, String> consumer;

         public void setup(Context context){
             consumer = StreamletKafkaConsumer.createConsumer();
         }

         @Override
         public Collection<FlightData> get() {
             FlightData nextMessage = null;

             /* Here, it's possible that the Kafka consumer has no records to consume. This will result in
             * no new messages before pushed into the queue, returning a null.
             * We only want to return a message to the Streamlet stream if the message is non-null
             */
             while(nextMessage == null) {
                 final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);

                 consumerRecords.forEach(record -> {
                     String jsonString = new String(record.value());
                     LOG.info("JSON String: " + jsonString);
                     FlightData newFlight = gson.fromJson(jsonString, FlightData.class);
                     messageQueue.add(newFlight);
                 });

                 consumer.commitAsync();

                 nextMessage = messageQueue.poll();
             }
             return Collections.singletonList(nextMessage);
         }

         public void cleanup() {

         }
    }

    private KafkaStreamletExample(){}

    public static void main(String[] args) throws Exception {
        Builder builder = Builder.newBuilder();

        Source<FlightData> kafkaSource = new KafkaSource();

        builder.newSource(kafkaSource)
                .consume(flight -> {
                    String logMessage = String.format("(Flight Number: %s",
                            flight.getICAO24()
                    );
                    LOG.info(logMessage);
        });

        Config.DeliverySemantics deliverySemantics = applyDeliverySemantices(args);

        Config config = Config.newBuilder()
                .setNumContainers(NUM_CONTAINERS) //Set the number of containers Heron will use
                .setPerContainerCpu(CPU) //How many CPUs do we want per container
                .setPerContainerRamInGigabytes(GIGABYTES_OF_RAM) //Ram per container
                .setDeliverySemantics(deliverySemantics)
                .build();

        String topologyName;

        if (args.length == 0) {
            throw new Exception("You must give a name to your toplogy before execution");
        } else {
            topologyName = args[0];
        }

        new Runner().run(topologyName, config, builder);

    }

    private static Config.DeliverySemantics applyDeliverySemantices(String[] args) throws Exception {
        if (args.length > 1) {
            switch (args[1]) {
                case "at-least-once":
                    System.out.println("Applying At-Least-Once Delivery Guarantees");
                    return Config.DeliverySemantics.ATLEAST_ONCE;
                case "at-most-once":
                    System.out.println("Applying At-Most-Once Delivery Guarantees");
                    return Config.DeliverySemantics.ATMOST_ONCE;
                case "effectively-once":
                    System.out.println("Applying Effectively-One Delivery Guarantees");
                    return Config.DeliverySemantics.EFFECTIVELY_ONCE;
                default:
                    throw new Exception("You've selected a delivery semantice preset that is not amongst the available options: " +
                            "at-most-once, at-least-once, effectively-once");
            }
        } else {
            return Config.DeliverySemantics.ATLEAST_ONCE;
        }
    }

}
