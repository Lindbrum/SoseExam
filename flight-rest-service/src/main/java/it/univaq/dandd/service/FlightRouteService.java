package it.univaq.dandd.service;

import java.util.List;
import it.univaq.dandd.model.FlightRoute;
import it.univaq.dandd.exception.RouteNotFoundException;

public interface FlightRouteService {
	List<FlightRoute> findAllRoutes();
	
	List<FlightRoute> findFutureRoutes(String departure, String arrival);
	
	FlightRoute findRouteById(long id) throws RouteNotFoundException;
}
