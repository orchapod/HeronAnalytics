package org.github.nfrush.json_mapping_example;

import java.util.List;

public class FlightDetails {
	private List<Flight> Flights;

	public List<Flight> getFlights() {
		return Flights;
	}

	public void setFlights(List<Flight> flight) {
		Flights = flight;
	}
}
