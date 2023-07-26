package it.univaq.dandd.car_soap_service.dao;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import it.univaq.dandd.car_soap_service.model.CarInfo;

@Repository
public interface CarInfoRepository extends ListCrudRepository<CarInfo, Long> {

	/**
	 * SELECT * FROM car_info WHERE location = :location.toLowercase() ORDER BY name
	 * @param location the city to search in (case insensitive)
	 * @return a list of cars
	 */
	List<CarInfo> findByLocationNameIgnoreCaseOrderByName(String location);
	
	/**
	 * SELECT * FROM car_info WHERE location = :location.toLowercase() AND name LIKE %:partialName% ORDER BY name
	 * @param location the city to search in (case insensitive)
	 * @param partialName (partial) name of the cars to look for (case insensitive)
	 * @return a list of cars
	 */
	List<CarInfo> findByLocationNameIgnoreCaseAndNameContainingIgnoreCaseOrderByName(String location, String partialName);
}
