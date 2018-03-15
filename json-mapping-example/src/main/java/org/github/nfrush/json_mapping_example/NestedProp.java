package org.github.nfrush.json_mapping_example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NestedProp {
	@JsonProperty("property_1")
	private String property_1;

	public String getProperty_1() {
		return property_1;
	}

	public void setProperty_1(String property_1) {
		this.property_1 = property_1;
	}
}
