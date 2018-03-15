package org.github.nfrush.json_mapping_example;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

import org.json.simple.JSONObject;

import com.esotericsoftware.minlog.Log;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.heron.streamlet.Context;
import com.twitter.heron.streamlet.SerializableTransformer;

public class JsonSerializer<T> implements SerializableTransformer<String, T> {
	private ObjectMapper mapper;
	private Class<T> cls;
	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setup(Context context) {
		mapper = new ObjectMapper();
	}
	
	@Override
	public void transform(String i, Consumer<T> consumer) {
		T jsonObj = null;
		Log.info("Transformer called");
		try {
			jsonObj = mapper.readValue(i.getBytes(), cls);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(jsonObj != null) {
			consumer.accept(jsonObj);
		}
	}
	
	public JsonSerializer(Class<T> cls) {
		this.cls = cls;
	}
}