package org.github.nfrush.json_mapping_example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Flight {
	private String altitude;
	@JsonProperty("nested")
	private NestedProp nested;

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public NestedProp getNested() {
		return nested;
	}

	public void setNested(NestedProp nested) {
		this.nested = nested;
	}
}
