package com.nfrush.kafkaConsumerStreamlet.models;

import java.util.List;

public class FlightData {
    private String ICAO24;
    private String CallSign;
    private String OriginCountry;
    private Long TimePosition;
    private Long LastContact;
    private Double Longitude;
    private Double Latitude;
    private Double GeoAltitude;
    private Boolean OnGround;
    private Double Velocity;
    private Double Heading;
    private Double VerticalRate;
    private List<Integer> Sensors;
    private Double BaroAltitude;
    private String Squawk;
    private Boolean SPI;
    private String PositionSource;

    public FlightData() {}

    public String getICAO24() {
        return ICAO24;
    }

    public String getCallSign() {
        return CallSign;
    }

    public String getOriginCountry() {
        return OriginCountry;
    }

    public Long getTimePosition() {
        return TimePosition;
    }

    public Long getLastContact() {
        return LastContact;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public Double getGeoAltitude() {
        return GeoAltitude;
    }

    public Boolean getOnGround() {
        return OnGround;
    }

    public Double getVelocity() {
        return Velocity;
    }

    public Double getHeading() {
        return Heading;
    }

    public Double getVerticalRate() {
        return VerticalRate;
    }

    public List<Integer> getSensors() {
        return Sensors;
    }

    public Double getBaroAltitude() {
        return BaroAltitude;
    }

    public String getSquawk() {
        return Squawk;
    }

    public Boolean getSPI() {
        return SPI;
    }

    public String getPositionSource() {
        return PositionSource;
    }

    public void setICAO24(String ICAO24) {
        this.ICAO24 = ICAO24;
    }

    public void setCallSign(String callSign) {
        CallSign = callSign;
    }

    public void setOriginCountry(String originCountry) {
        OriginCountry = originCountry;
    }

    public void setTimePosition(Long timePosition) {
        TimePosition = timePosition;
    }

    public void setLastContact(Long lastContact) {
        LastContact = lastContact;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public void setGeoAltitude(Double geoAltitude) {
        GeoAltitude = geoAltitude;
    }

    public void setOnGround(Boolean onGround) {
        OnGround = onGround;
    }

    public void setVelocity(Double velocity) {
        Velocity = velocity;
    }

    public void setHeading(Double heading) {
        Heading = heading;
    }

    public void setVerticalRate(Double verticalRate) {
        VerticalRate = verticalRate;
    }

    public void setSensors(List<Integer> sensors) {
        Sensors = sensors;
    }

    public void setBaroAltitude(Double baroAltitude) {
        BaroAltitude = baroAltitude;
    }

    public void setSquawk(String squawk) {
        Squawk = squawk;
    }

    public void setSPI(Boolean SPI) {
        this.SPI = SPI;
    }

    public void setPositionSource(String positionSource) {
        PositionSource = positionSource;
    }
}
