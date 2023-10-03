package it.univaq.dandd.service;

import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.management.loading.PrivateClassLoader;

import org.hibernate.boot.model.naming.ImplicitNameSource;
import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import it.univaq.dandd.dao.ItineraryRepository;
import it.univaq.dandd.exception.TemplateNotFoundException;
import it.univaq.dandd.model.ItineraryInfo;

@Service
public class ItineraryServiceImpl implements ItineraryService {
	
	
	private final ItineraryRepository itineraryRepository;
	private final RestTemplate restTemplate;
	private static final Logger logger = LoggerFactory.getLogger(ItineraryServiceImpl.class);
	
	@Autowired
	public ItineraryServiceImpl(ItineraryRepository itineraryRepository, RestTemplate restTemplate) {
		this.itineraryRepository = itineraryRepository;
		this.restTemplate = restTemplate;
	}
	
	@Override
	public List<ItineraryInfo> findAllBookings() {
		return itineraryRepository.findAll();
	}
	
	//ritorna info su un itinerary
	@Override
	public Map<String, Object> ask_providers_info_on_itinerary(ItineraryInfo itineraryInfo) {
		long id = itineraryInfo.getId();
		int service_id = itineraryInfo.getServiceId();
		String service_type = itineraryInfo.getServiceType();
	    String targetUrl;
	    
	    if(service_type.equals("hotel")) {
	    	targetUrl = "http://localhost:8080/api/hotels/"+service_id+"/";
		}
	    else if (service_type.equals("boat")) {
	    	targetUrl = "http://localhost:8080/api/boats/"+service_id+"/";
		}
	    else {
	    	targetUrl = "http://localhost:8080/api/flights/"+service_id+"/";
		}
	    Map<String, Long> urlParams = new HashMap<>();
	  
    	ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
        	    targetUrl,
        	    HttpMethod.GET,
        	    null,
        	    new  ParameterizedTypeReference<Map<String, Object>>() {}

        	);
    	if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            responseBody.put("itinerary_id", id);
            responseBody.put("service_type", service_type);
            return responseBody;

    	}
    	
    	 else {

     	    throw new TemplateNotFoundException("Error in the GET request");
     	}
    	
	}
		
	
    @Override
    public List<Map<String, Object>> find_Original_Booking_Info() {
    	List<Map<String, Object>> original_data = new ArrayList<>();
    	List<ItineraryInfo> itinerary_data = itineraryRepository.findAll();
    	for (ItineraryInfo itineraryInfo : itinerary_data) {    		
   		    Map<String, Object> response=ask_providers_info_on_itinerary(itineraryInfo);
            original_data.add(response);            
    	}
		return original_data;	

    }
    
    @Override
    public Map<String, Object> find_last(){
    	List<ItineraryInfo> itinerary_data =itineraryRepository.findAll();
        
    	ItineraryInfo lastItineraryInfo = itinerary_data.stream()
                .max(Comparator.comparing(ItineraryInfo::getId))
                .orElse(null);
    	
        Map<String, Object> providerData = ask_providers_info_on_itinerary(lastItineraryInfo);    	
    	
        if (providerData.containsKey("locationName")) {
            String locationName = (String) providerData.get("locationName");
            Map<String, Object> result = new HashMap<>();
            result.put("LastLocation", locationName);
            return result;
        } else {

            String arrivalName = (String) providerData.get("arrivalName");
            Map<String, Object> result = new HashMap<>();
            result.put("LastLocation", arrivalName);
            return result;
        }
    	
    }
    
    @Override
    public List<Map<String, Object>> show_next_option() {
        List<Map<String, Object>> available_services = new ArrayList<>();
        Map<String, Object> last_location_json = find_last();
        
        String last_location = (String) last_location_json.get("LastLocation");
        String[] urls = {
            "http://localhost:8080/api/hotels/all/",
            "http://localhost:8080/api/boats/all/",
            "http://localhost:8080/api/flights/all/"
        };

        for (String targetUrl : urls) {
        	
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                List<Map<String, Object>> responseBodyList = response.getBody();
                
                for (Map<String, Object> responseBody : responseBodyList) {
                    if (responseBody.containsKey("locationName")) {
                        String locationName = (String) responseBody.get("locationName");
                        if (locationName.equals(last_location)) {
                        	responseBody.put("service_type", "hotel");
                            available_services.add(responseBody);
                        }
                    } else {
                        String arrivalName = (String) responseBody.get("departureName");
                        if (arrivalName.equals(last_location)) {
                        	if (targetUrl.contains("boats")) {
                                responseBody.put("service_type", "boat");
                            } 
                        	else {
                                responseBody.put("service_type", "flight");
                            }

                            available_services.add(responseBody);
                        }
                    }
                }
            } else {
                throw new TemplateNotFoundException("Error in the GET request");
            }
        }
        return available_services;
    }
    
    @Override
    public void choose_next_option(int service_id,String service_type) throws Exception {
    	List<Map<String, Object>> available_options= show_next_option();
        boolean found = false;
        
        for (Map<String, Object> option : available_options) {
            if (option.get("service_type").equals(service_type) && option.get("id").equals(service_id)) {
                found = true;
                break;
            }
        }

        if (!found) {
            throw new Exception("Not one of the options");
        }
    
    	
    	
    	ItineraryInfo newItinerary = new ItineraryInfo();
    	
    	 
    	if (service_type.equals("boat")) {
    		newItinerary.setServiceType("boat");
    	}
    	else if(service_type.equals("flight")){
    		newItinerary.setServiceType("flight");
    	}
    	else {
    		newItinerary.setServiceType("hotel");
    	}
    	newItinerary.setServiceId(service_id);
    	itineraryRepository.save(newItinerary);
    	
    	
    }
    
    @Override 
    public void add_car_booking(int service_id) {
    	ItineraryInfo newItinerary = new ItineraryInfo();
    	newItinerary.setServiceType("car");
    	itineraryRepository.save(newItinerary);

    }

 
    
}

    
 
