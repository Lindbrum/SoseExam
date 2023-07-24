package it.univaq.dandd.service;

import java.util.List;
import it.univaq.dandd.model.HotelSchema;
import it.univaq.dandd.exception.HotelNotFoundException;

public interface HotelService {
	List<HotelSchema> findAllHotels();
	
	List<HotelSchema> findSpecificHotels(String Location, String name);//più di un hotel con lo stesso nome?
	
	HotelSchema findHotelById(long id) throws HotelNotFoundException;
	
	
}