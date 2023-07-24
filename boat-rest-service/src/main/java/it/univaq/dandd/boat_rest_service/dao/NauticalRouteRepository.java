package it.univaq.dandd.boat_rest_service.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import it.univaq.dandd.boat_rest_service.model.NauticalRoute;

public interface NauticalRouteRepository extends ListCrudRepository<NauticalRoute, Long> {

	List<NauticalRoute> findAllByDepartureDatetimeGreaterThanOrderByDepartureDatetime(LocalDateTime datetime);
	
	List<NauticalRoute> findAllByDepartureNameIgnoreCaseAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(String departure, LocalDateTime datetime);
	
	List<NauticalRoute> findAllByArrivalNameIgnoreCaseAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(String arrival, LocalDateTime datetime);
	
	List<NauticalRoute> findAllByDepartureNameIgnoreCaseAndArrivalNameIgnoreCaseAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(String departure, String arrival, LocalDateTime datetime);
}
