package it.univaq.dandd.boat_rest_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import it.univaq.dandd.boat_rest_service.dao.NauticalRouteRepository;
import it.univaq.dandd.boat_rest_service.exception.RouteNotFoundException;
import it.univaq.dandd.boat_rest_service.model.NauticalRoute;

@Service
public class NauticalRouteServiceImpl implements NauticalRouteService {

	private final NauticalRouteRepository nauticalRouteRepository;
	
	//Spring Boot can infer @Autowired when there is a single constructor in a bean
	//@Autowired
	public NauticalRouteServiceImpl(NauticalRouteRepository nauticalRouteRepository) {
		this.nauticalRouteRepository = nauticalRouteRepository;
	}
	
	@Override
	public List<NauticalRoute> findAllRoutes() {
		
		return nauticalRouteRepository.findAll();
	}

	@Override
	public List<NauticalRoute> findFutureRoutes(String departure, String arrival) {
		LocalDateTime now = LocalDateTime.now();
		//Count how many parameters are set, converting the Strings to lowercase
		int parametersSet = 0;
		//departing city
		if(departure!=null && !departure.isBlank()) {
			departure = departure.toLowerCase();
			parametersSet++;
		}
		//arriving city
		if(arrival!=null && !arrival.isBlank()) {
			arrival = arrival.toLowerCase();
			parametersSet++;
		}
		
		List<NauticalRoute> result;
		
		switch (parametersSet) {
		case 0: {
			//return unfiltered list
			result = nauticalRouteRepository.findAllByDepartureDatetimeGreaterThanOrderByDepartureDatetime(now);
			break;
		}
		case 1: {
			//filter either by departure or arrival
			if(departure!=null && !departure.isBlank()) {
				//filter by departure
				result = nauticalRouteRepository.findAllByDepartureNameIgnoreCaseAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(departure, now);
			}else {
				//filter by arrival
				result = nauticalRouteRepository.findAllByArrivalNameIgnoreCaseAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(arrival, now);
			}
			break;
		}
		case 2: {
			//filter by both departure and arrival
			result = nauticalRouteRepository.findAllByDepartureNameIgnoreCaseAndArrivalNameIgnoreCaseAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(departure, arrival, now);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + parametersSet);
		}
		
		return result;
	}
	

	@Override
	public NauticalRoute findRouteById(long id) throws RouteNotFoundException{
		// TODO Auto-generated method stub
		return nauticalRouteRepository.findById(id).orElseThrow(() -> new RouteNotFoundException("Route with ID = "+id+" not found."));
	}

}
