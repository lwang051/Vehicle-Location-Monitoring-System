package com.lingbo.simulation_service.model;




import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceLocation {

    private String id;
    private String address1;
    private String address2;
    private String city;
    @JsonUnwrapped
    private final Point point;
    private String location;
    private String state;
    private String zip;
    private String type;

    @SuppressWarnings("unused")
    private ServiceLocation() {
        this.point = new Point(0d, 0d);
    }

    @JsonCreator
    public ServiceLocation(@JsonProperty("latitude") double latitude, @JsonProperty("longitude") double longitude) {
        this.point = new Point(latitude, longitude);
    }

}
