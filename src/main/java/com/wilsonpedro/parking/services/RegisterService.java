package com.wilsonpedro.parking.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilsonpedro.parking.enums.EntranceAndExit;
import com.wilsonpedro.parking.enums.VehicleStatus;
import com.wilsonpedro.parking.models.Register;
import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.repositories.RegisterRepository;

@Service
public class RegisterService {

	@Autowired
	private RegisterRepository registerRepository;
	
	public Register save(Vehicle vehicle) {
		Register register = prepareRegistry(vehicle);
		return registerRepository.save(register);
	}
	
	public Register prepareRegistry(Vehicle vehicle) {
		Register register = new Register();
		register.setVehicle(vehicle);
		var EntranceAndExit = EntranceOrExit(vehicle.getStatus());
		register.setEntranceAndExit(EntranceAndExit);
		register.setLocalDateTime(LocalDateTime.now());
		
		return register;
	}

	private EntranceAndExit EntranceOrExit(VehicleStatus status) {
		
		EntranceAndExit entranceAndExit = EntranceAndExit.UNDEFINED;
		
		if(status.equals(VehicleStatus.PARKED))
			entranceAndExit = EntranceAndExit.ENTRANCE;
		else if(status.equals(VehicleStatus.NOT_PARKED))
			entranceAndExit = EntranceAndExit.EXIT;
			
		return entranceAndExit;
	}
}
