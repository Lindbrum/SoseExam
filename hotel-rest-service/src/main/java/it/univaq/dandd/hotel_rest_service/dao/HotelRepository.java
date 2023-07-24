package it.univaq.dandd.hotel_rest_service.dao;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import it.univaq.dandd.hotel_rest_service.model.HotelInfo;

public interface HotelRepository extends ListCrudRepository<HotelInfo, Long> {

	List<HotelInfo> findAllByHotelNameContainingIgnoreCaseOrderByLocationName(String hotelName);
	List<HotelInfo> findAllByLocationNameIgnoreCaseOrderByHotelName(String locationName);
	List<HotelInfo> findAllByLocationNameIgnoreCaseAndHotelNameContainingIgnoreCaseOrderByHotelName(String locationName, String hotelName);
	
}