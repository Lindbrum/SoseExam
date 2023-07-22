package it.univaq.dandd.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import it.univaq.dandd.dao.FlightTemplateRepository;
import it.univaq.dandd.exception.RouteNotFoundException;
import it.univaq.dandd.model.FlightRoute;

@Service
public class FlightRouteImpl implements FlightRouteService {

	public FlightRouteImpl(FlightTemplateRepository flightTemplateRepository) {
		this.flightTemplateRepository = flightTemplateRepository;
	}
	
	private final FlightTemplateRepository flightTemplateRepository;
	
	@Override
	public List<FlightRoute> findAllRoutes() {
		return flightTemplateRepository.findAll();
	}
	
	int parametersSet = 0;

	@Override
	public List<FlightRoute> findFutureRoutes(String departure, String arrival) {
		LocalDateTime now=LocalDateTime.now();
		
		if(departure!=null && !departure.isBlank()) {
			departure = departure.toLowerCase();
			parametersSet++;
		}
		
		if(arrival!=null && !arrival.isBlank()) {
			arrival = arrival.toLowerCase();
			parametersSet++;
		}
		
		List<FlightRoute> result;
		
		switch(parametersSet) {
		case 0:{
			result = flightTemplateRepository.findAllbyDepartureDatetimeGreaterThanOrderByDepartureDatetime(now);
			break;
		}
		case 1:{
			if(departure!=null && !departure.isBlank()) {
				result = flightTemplateRepository.findAllbyDepartureNameAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(departure,now);
			}
			else {
				result = flightTemplateRepository.findAllbyArrivalNameAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(arrival, now);
			}
			
		}
		case 2:{
			result = flightTemplateRepository.findAllbyDepartureNameAndArrivalNameAndDepartureDatetimeGreaterThanOrderByDepartureDatetime(departure, arrival, now);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + parametersSet);
		}
		
		return result;
		
	}

	@Override
	public FlightRoute findRouteById(long id) throws RouteNotFoundException {
		// TODO Auto-generated method stub
		return flightTemplateRepository.findById(id).orElseThrow(() ->  new RouteNotFoundException("Route with ID = "+id+" not found."));
	}

}
