package it.univaq.dandd.dao;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import it.univaq.dandd.model.HotelSchema;

public interface HotelRepository extends ListCrudRepository<HotelSchema, Long> {

	List<HotelSchema> findAllbyHotelNameOrderByHotelName(String HotelName);
	List<HotelSchema> findAllbyLocatioNameOrderByLocationName(String LocationName);

	
}