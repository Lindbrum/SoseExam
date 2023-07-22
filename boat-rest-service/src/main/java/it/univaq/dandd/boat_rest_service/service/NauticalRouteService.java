package it.univaq.dandd.boat_rest_service.service;

import java.util.List;

import it.univaq.dandd.boat_rest_service.exception.RouteNotFoundException;
import it.univaq.dandd.boat_rest_service.model.NauticalRoute;

public interface NauticalRouteService {
	
	/**
	 * Return all nautical routes registered in the DB
	 * @return A list of all nautical routes registered
	 */
	List<NauticalRoute> findAllRoutes();
	
	/**
	 * Return all future nautical routes, eventually filtered by departing and/or arriving city
	 * @param departure optional departing city name (case insensitive)
	 * @param arrival optional arriving city name (cas insensitive)
	 * @return A list of future nautical routes, eventually filtered by departing and/or arriving city.
	 */
	List<NauticalRoute> findFutureRoutes(String departure, String arrival);
	
	/**
	 * Return the nautical route matching the ID
	 * @param id the nautical route ID
	 * @return the nautical route matching the ID
	 * @throws RouteNotFoundException if there is no match for the ID.
	 */
	NauticalRoute findRouteById(long id) throws RouteNotFoundException;
}
