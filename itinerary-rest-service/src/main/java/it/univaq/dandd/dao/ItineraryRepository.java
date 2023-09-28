package it.univaq.dandd.dao;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import it.univaq.dandd.model.ItineraryInfo;

public interface ItineraryRepository extends ListCrudRepository<ItineraryInfo, Long> {

	

	
}