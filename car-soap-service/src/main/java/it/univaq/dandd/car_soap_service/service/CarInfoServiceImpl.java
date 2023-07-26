package it.univaq.dandd.car_soap_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import it.univaq.dandd.car_soap_service.dao.CarInfoRepository;
import it.univaq.dandd.car_soap_service.exception.CarNotFoundException;
import it.univaq.dandd.car_soap_service.exception.IllegalDateException;
import it.univaq.dandd.car_soap_service.model.CarInfo;

@Service
public class CarInfoServiceImpl implements CarInfoService {

	private final CarInfoRepository carInfoRepository;
	
	public CarInfoServiceImpl(CarInfoRepository carInfoRepository) {
		this.carInfoRepository = carInfoRepository;
	}

	@Override
	public List<CarInfo> getAll() {
		
		return carInfoRepository.findAll();
	}

	@Override
	public List<CarInfo> find(String location, String name) {
		
		List<CarInfo> result;
		//Check if we are filtering by name too
		if(name != null && !name.isBlank()) {
			result = carInfoRepository.findByLocationNameIgnoreCaseAndNameContainingIgnoreCaseOrderByName(location, name);
		}else {
			//filter only by city
			result = carInfoRepository.findByLocationNameIgnoreCaseOrderByName(location);
		}
		return result;
	}

	@Override
	public CarInfo findById(long id) throws CarNotFoundException {
		
		return carInfoRepository.findById(id).orElseThrow(()-> new CarNotFoundException(String.format("Car with ID=%s not found.", id)));
	}

	@Override
	public CarInfo registerBooking(long id, LocalDateTime until) throws CarNotFoundException, IllegalDateException {
		
		if(until.isBefore(LocalDateTime.now())) {
			throw new IllegalDateException(String.format("The rental expiration cannot be in the past! (Datetime passed was %s)", until));
		}
		
		CarInfo car = findById(id);
		
		car.setAvailableFrom(until);
		
		return carInfoRepository.save(car);
	}

	@Override
	public CarInfo removeBooking(long id) throws CarNotFoundException, IllegalDateException {
		
		CarInfo car = findById(id);
		if(car.getAvailableFrom().isBefore(LocalDateTime.now())) {
			throw new IllegalDateException(String.format("This rental has already expired. (Expired on {})", car.getAvailableFrom().toString()));
		}
		//Set rental end to now
		car.setAvailableFrom(LocalDateTime.now());
		return carInfoRepository.save(car);
	}

}
