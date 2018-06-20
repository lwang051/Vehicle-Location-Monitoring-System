package message_sink_service.model;



import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrentPosition {

	private String vin;
	private Point location;
	private VehicleStatus vehicleStatus = VehicleStatus.NONE;
	private Double speed;
	private Double heading;
	private FaultCode faultCode;
	private ServiceLocation serviceLocation;
}
