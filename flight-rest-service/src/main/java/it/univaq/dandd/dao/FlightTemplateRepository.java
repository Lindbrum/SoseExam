package it.univaq.dandd.dao;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;

import it.univaq.dandd.model.FlightRoute;

public interface FlightTemplateRepository extends ListCrudRepository<FlightRoute, Long> {
	
	List<FlightRoute> findAllbyDepartureDatetimeGreaterThanOrderByDepartureDatetime(LocalDateTime datetime);
	List<FlightRoute> findAllbyDepartureNameAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(String departure,LocalDateTime datetime);
	List<FlightRoute> findAllbyArrivalNameAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(String arrival,LocalDateTime datetime);
	List<FlightRoute> findAllbyDepartureNameAndArrivalNameAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(String departure,String arrival,LocalDateTime datetime);
}
