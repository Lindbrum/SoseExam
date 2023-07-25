package it.univaq.dandd.flight_rest_service.dao;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import it.univaq.dandd.flight_rest_service.model.FlightRoute;

public interface FlightRouteRepository extends ListCrudRepository<FlightRoute, Long> {
	
	List<FlightRoute> findAllByDepartureDatetimeGreaterThanOrderByDepartureDatetime(LocalDateTime datetime);
	List<FlightRoute> findAllByDepartureNameIgnoreCaseAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(String departure, LocalDateTime datetime);
	List<FlightRoute> findAllByArrivalNameIgnoreCaseAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(String arrival, LocalDateTime datetime);
	List<FlightRoute> findAllByDepartureNameIgnoreCaseAndArrivalNameIgnoreCaseAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(String departure, String arrival, LocalDateTime datetime);
}
