package it.univaq.dandd.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import it.univaq.dandd.exception.TemplateNotFoundException;
import it.univaq.dandd.model.ItineraryInfo;

public interface ItineraryService {

	List<ItineraryInfo> findAllBookings();


	List<Map<String, Object>> find_Original_Booking_Info();


	Map<String, Object> find_last();

	Map<String, Object> ask_providers_info_on_itinerary(ItineraryInfo itineraryInfo);


	List<Map<String, Object>> show_next_option();


	void choose_next_option(int id, String service_type) throws Exception;


	String show_all_cities();



	
}
