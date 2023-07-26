package it.univaq.dandd.car_soap_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.JsonNode;

@FeignClient("itinerary-rest-service")
public interface ItineraryFeignClient {
	
	public static final String ERROR_STATUS_PROPERTY = "status";
	public static final String LOCATION_PROPERTY = "locationName";

	/**
	 * Fetch the current itinerary location from the itinerary prosumer
	 * @return a JsonNode object containing the location info
	 */
	@GetMapping("itinerary/currentLocation/")
	JsonNode getCurrentLocation();

}
