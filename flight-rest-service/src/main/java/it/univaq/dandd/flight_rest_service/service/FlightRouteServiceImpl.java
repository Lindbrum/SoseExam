package it.univaq.dandd.flight_rest_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import it.univaq.dandd.flight_rest_service.dao.FlightRouteRepository;
import it.univaq.dandd.flight_rest_service.exception.RouteNotFoundException;
import it.univaq.dandd.flight_rest_service.model.FlightRoute;

@Service
public class FlightRouteServiceImpl implements FlightRouteService {

	private final FlightRouteRepository flightRouteRepository;
	
	//Spring Boot can infer @Autowired when there is a single constructor in a bean
	//@Autowired
	public FlightRouteServiceImpl(FlightRouteRepository flightRouteRepository) {
		this.flightRouteRepository = flightRouteRepository;
	}
	
	
	@Override
	public List<FlightRoute> findAllRoutes() {
		return flightRouteRepository.findAll();
	}
	

	@Override
	public List<FlightRoute> findFutureRoutes(String departure, String arrival) {
		
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
		
		List<FlightRoute> result;
		
		switch(parametersSet) {
		case 0:{
			//return unfiltered list
			result = flightRouteRepository.findAllByDepartureDatetimeGreaterThanOrderByDepartureDatetime(now);
			
			break;
		}
		case 1:{
			//filter either by departure or arrival
			if(departure!=null && !departure.isBlank()) {
				//filter by departure
				result = flightRouteRepository.findAllByDepartureNameIgnoreCaseAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(departure,now);
			}
			else {
				//filter by arrival
				result = flightRouteRepository.findAllByArrivalNameIgnoreCaseAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(arrival, now);
			}
			
			break;
			
		}
		case 2:{
			//filter by both departure and arrival
			result = flightRouteRepository.findAllByDepartureNameIgnoreCaseAndArrivalNameIgnoreCaseAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(departure, arrival, now);
			
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + parametersSet);
		}
		
		return result;
		
	}

	@Override
	public FlightRoute findRouteById(long id) throws RouteNotFoundException {
		
		return flightRouteRepository.findById(id)
				.orElseThrow(() ->  new RouteNotFoundException("Flight route with ID = "+id+" not found."));
	}

}
