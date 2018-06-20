package message_sink_service.model;



import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ServiceLocation {

	private String id;
	private String address1;
	private String address2;
	private String city;

	@JsonIgnore
	private final Point location;

	private String state;
	private String zip;
	private String type;

	@JsonCreator
	public ServiceLocation(@JsonProperty("latitude") double latitude, @JsonProperty("longitude") double longitude) {
		this.location = new Point(latitude, longitude);
	}

	public double getLatitude() {
		return this.location.getLatitude();
	}

	public double getLongitude() {
		return this.location.getLongitude();
	}
}
