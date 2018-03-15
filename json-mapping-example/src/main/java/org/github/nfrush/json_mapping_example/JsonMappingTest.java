package org.github.nfrush.json_mapping_example;

import com.esotericsoftware.minlog.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.heron.streamlet.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public final class JsonMappingTest {
    private static final Logger LOG = Logger.getLogger(JsonMappingTest.class.getName());
    private static final float CPU = 2.0f;
    private static final long GIGABYTES_OF_RAM = 6;
    private static final int NUM_CONTAINERS = 2;
    
    private static final List<String> JSON_1 = Arrays.asList(
    		"{\"altitude\": \"1\", \"nested\": {\"property_1\": \"hi\"}}",
    		"{\"altitude\": \"2\", \"nested\": {\"property_1\": \"hi\"}}",
    		"{\"altitude\": \"4\", \"nested\": {\"property_1\": \"hi\"}}"
    );

    private static final List<String> JSON_2 = Arrays.asList(
    		"{\"altitude\": \"3\"}"
    );

    private static <T> T randomFromList(List<T> ls) {
        return ls.get(ThreadLocalRandom.current().nextInt(ls.size()));
    }
    
    private static class TestBinaryArrySource<T> implements Source<T> {
    	private ObjectMapper mapper;
    	private Class<T> cls;

		@Override
		public void cleanup() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Collection<T> get() {
			String next = randomFromList(JSON_1);
			T obj = null;
			while(obj == null) {
				try {
					obj = mapper.readValue(next.getBytes(), cls);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return Collections.singletonList(obj);
		}

		@Override
		public void setup(Context context) {
			mapper = new ObjectMapper();
		}
		
		public TestBinaryArrySource(Class<T> cls) {
			this.cls = cls;
		}
    	
    }

    private JsonMappingTest() {}

	public static void main(String[] args) throws Exception {
        Builder builder = Builder.newBuilder();
        
        //Streamlet<Flight> flightSource = builder.newSource(new TestBinaryArrySource<Flight>(Flight.class));

        /*flightSource
        		.consume(x -> {
        			if(x == null) {
        				LOG.info("Object was null");
        				return;
        			}
        			if(x.getNested() == null) {
        				LOG.info("Nested Object was null");
        				return;
        			}
        			if(x.getNested().getProperty_1() == null) {
        				LOG.info("Nested Object Property was null");
        				return;
        			}
        			LOG.info(x.getNested().getProperty_1());
        		});*/
        
        Streamlet<String> jsonSource = builder.newSource(() -> randomFromList(JSON_1));
        
        jsonSource
        	.transform(new JsonSerializer<Flight>(Flight.class))
        	.consume(x -> {
        		Log.info(x.getNested().getProperty_1());
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
