package it.univaq.dandd.hotel_rest_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.univaq.dandd.hotel_rest_service.model.HotelInfo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HotelControllerTests {
	
	//Test REST template
	@Autowired
    private TestRestTemplate restTemplate;
	
	//Injects the randomly generated HTTP port
    @LocalServerPort
    private int randomServerPort;
    
    @Test
    void should_return_all_hotels() {
        HotelInfo[] hotels = restTemplate.getForObject("http://localhost:" + randomServerPort + "/hotels/all/", HotelInfo[].class);
        
        assertNotNull(hotels);
        assertEquals(12, hotels.length);
    }
    
    @Test
    void should_return_all_durazzo_hotels() {
    	HotelInfo[] hotels = restTemplate.getForObject("http://localhost:" + randomServerPort + "/hotels/?location=durazzo", HotelInfo[].class);
        
        assertNotNull(hotels);
        assertEquals(2, hotels.length);
        assertEquals(hotels[0].getLocationName().toLowerCase(), "durazzo");
    }
    
    @Test
    void should_hotel_route_with_id_1() {
    	Long id = 1L;
    	String url = "http://localhost:" + randomServerPort + "/hotels/{id}/";
        ResponseEntity<HotelInfo> response = restTemplate.getForEntity(url, HotelInfo.class, id);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
    }
}
