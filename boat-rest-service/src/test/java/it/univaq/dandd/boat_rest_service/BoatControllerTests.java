package it.univaq.dandd.boat_rest_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import it.univaq.dandd.boat_rest_service.model.NauticalRoute;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BoatControllerTests {
	
	//Test REST template
	@Autowired
    private TestRestTemplate restTemplate;
	
	//Injects the randomly generated HTTP port
    @LocalServerPort
    private int randomServerPort;
    
    private final String template = "The sum is: %s!";
    
    @Test
    void should_return_all_routes() {
        List<NauticalRoute> routes = restTemplate.getForObject("http://localhost:" + randomServerPort + "/boats/all/", List.class);
        
        assertNotNull(routes);
        assertEquals(2, routes.size());
    }
    
    @Test
    void should_return_all_pescara_routes() {
        List<NauticalRoute> routes = restTemplate.getForObject("http://localhost:" + randomServerPort + "/boats/?departure=pescara", List.class);
        
        assertNotNull(routes);
        assertEquals(1, routes.size());
        assertEquals(routes.get(0).getDepartureName().toLowerCase(), "pescara");
    }
    
    @Test
    void should_return_route_with_id_1() {
        NauticalRoute route = restTemplate.getForObject("http://localhost:" + randomServerPort + "/boats/1/", NauticalRoute.class);
        
        assertNotNull(route);
        assertEquals(1, route.getId());
    }
}
