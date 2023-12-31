package it.univaq.dandd.flight_rest_service;

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

import it.univaq.dandd.flight_rest_service.model.FlightRoute;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FlightControllerTests {
	
	//Test REST template
	@Autowired
    private TestRestTemplate restTemplate;
	
	//Injects the randomly generated HTTP port
    @LocalServerPort
    private int randomServerPort;
    
    @Test
    void should_return_all_routes() {
        FlightRoute[] routes = restTemplate.getForObject("http://localhost:" + randomServerPort + "/flights/all/", FlightRoute[].class);
        
        assertNotNull(routes);
        assertEquals(2, routes.length);
    }
    
    @Test
    void should_return_all_incoming_catania_routes() {
    	FlightRoute[] routes = restTemplate.getForObject("http://localhost:" + randomServerPort + "/flights/?arrival=catania", FlightRoute[].class);
        
        assertNotNull(routes);
        assertEquals(1, routes.length);
        assertEquals(routes[0].getArrivalName(), "Catania");
    }
    
    @Test
    void should_return_route_with_id_1() {
    	Long id = 1L;
    	String url = "http://localhost:" + randomServerPort + "/flights/{id}/";
        ResponseEntity<FlightRoute> response = restTemplate.getForEntity(url, FlightRoute.class, id);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
    }
}
