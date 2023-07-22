package it.univaq.dandd.boat_rest_service;

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

import it.univaq.dandd.boat_rest_service.model.NauticalRoute;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BoatControllerTests {
	
	//Test REST template
	@Autowired
    private TestRestTemplate restTemplate;
	
	//Injects the randomly generated HTTP port
    @LocalServerPort
    private int randomServerPort;
    
    @Test
    void should_return_all_routes() {
        NauticalRoute[] routes = restTemplate.getForObject("http://localhost:" + randomServerPort + "/boats/all/", NauticalRoute[].class);
        
        assertNotNull(routes);
        assertEquals(2, routes.length);
    }
    
    @Test
    void should_return_all_pescara_routes() {
    	NauticalRoute[] routes = restTemplate.getForObject("http://localhost:" + randomServerPort + "/boats/?departure=pescara", NauticalRoute[].class);
        
        assertNotNull(routes);
        assertEquals(1, routes.length);
        assertEquals(routes[0].getDepartureName().toLowerCase(), "pescara");
    }
    
    @Test
    void should_return_route_with_id_1() {
    	Long id = 1L;
    	String url = "http://localhost:" + randomServerPort + "/boats/{id}/";
        ResponseEntity<NauticalRoute> response = restTemplate.getForEntity(url, NauticalRoute.class, id);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
    }
}
