package it.univaq.dandd.hotel_rest_service.service;

import java.util.List;

import it.univaq.dandd.hotel_rest_service.exception.HotelNotFoundException;
import it.univaq.dandd.hotel_rest_service.model.HotelInfo;

public interface HotelService {
	List<HotelInfo> findAllHotels();
	
	List<HotelInfo> findSpecificHotels(String Location, String name);//più di un hotel con lo stesso nome?
	
	HotelInfo findHotelById(long id) throws HotelNotFoundException;
	
	
}