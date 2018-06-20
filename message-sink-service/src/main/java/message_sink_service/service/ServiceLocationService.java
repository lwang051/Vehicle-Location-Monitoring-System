package message_sink_service.service;

import message_sink_service.model.CurrentPosition;

public interface ServiceLocationService {

	void updateServiceLocations(CurrentPosition currentPosition);

}
