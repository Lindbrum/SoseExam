package it.univaq.dandd.car_soap_service.service;

import java.time.LocalDateTime;
import java.util.List;

import it.univaq.dandd.car_soap_service.exception.CarNotFoundException;
import it.univaq.dandd.car_soap_service.exception.IllegalDateException;
import it.univaq.dandd.car_soap_service.model.CarInfo;

public interface CarInfoService {

	/**
	 * Return all car records from the datasource.
	 * @return a list of cars.
	 */
	List<CarInfo> getAll();
	
	/**
	 * Search for cars by location and/or (eventually partial) name.
	 * @param location the city to search cars in.
	 * @param name (partial) name of the car.
	 * @return a list of cars matching the criteria.
	 */
	List<CarInfo> find(String location, String name);
	
	/**
	 * Search the car matching the ID.
	 * @param id the ID to search.
	 * @return the car matching the ID.
	 * @throws CarNotFoundException if not found.
	 */
	CarInfo findById(long id) throws CarNotFoundException;
	
	/**
	 * Register a car booking.
	 * @param id the id of the car to book.
	 * @param until the expected datetime for rental expiry.
	 * @return the updated car info.
	 * @throws CarNotFoundException If ID doesn't match.
	 * @throws IllegalDateException If the rental date is in the past.
	 */
	CarInfo registerBooking(long id, LocalDateTime until) throws CarNotFoundException, IllegalDateException;
	
	/**
	 * Cancel a car booking.
	 * @param id
	 * @return the updated car info.
	 * @throws CarNotFoundException If ID doesn't match.
	 * @throws IllegalDateException If the rental date is in the past.
	 */
	CarInfo removeBooking(long id) throws CarNotFoundException, IllegalDateException;
}
