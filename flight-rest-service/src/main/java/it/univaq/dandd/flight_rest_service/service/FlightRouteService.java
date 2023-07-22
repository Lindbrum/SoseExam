package it.univaq.dandd.flight_rest_service.service;

import java.util.List;

import it.univaq.dandd.flight_rest_service.exception.RouteNotFoundException;
import it.univaq.dandd.flight_rest_service.model.FlightRoute;

public interface FlightRouteService {
	
	/**
	 * Return all flight routes registered in the DB
	 * @return A list of all flight routes registered
	 */
	List<FlightRoute> findAllRoutes();
	
	/**
	 * Return all future flight routes, eventually filtered by departing and/or arriving city
	 * @param departure optional departing city name (case insensitive)
	 * @param arrival optional arriving city name (case insensitive)
	 * @return A list of future flight routes, eventually filtered by departing and/or arriving city.
	 */
	List<FlightRoute> findFutureRoutes(String departure, String arrival);
	
	/**
	 * Return the flight route matching the ID
	 * @param id the flight route ID
	 * @return the flight route matching the ID
	 * @throws RouteNotFoundException if there is no match for the ID.
	 */
	FlightRoute findRouteById(long id) throws RouteNotFoundException;
}
