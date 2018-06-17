package com.lingbo.simulator.service;

import com.lingbo.simulator.domain.CurrentPosition;

public interface PositionService {
	
	void processPositionInfo(long id,
            CurrentPosition currentPosition,
            boolean exportPositionsToKml,
            boolean sendPositionsToIngestionSerice);
	
}
