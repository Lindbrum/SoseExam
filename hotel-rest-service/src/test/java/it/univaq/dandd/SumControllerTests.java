package it.univaq.dandd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import it.univaq.dandd.model.Sum;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SumControllerTests {
	
	//Test REST template
	@Autowired
    private TestRestTemplate restTemplate;
	
	//Injects the randomly generated HTTP port
    @LocalServerPort
    private int randomServerPort;
    
    private final String template = "The sum is: %s!";
    
    @Test
    void should_return_8_plus_4_answer() {
        Sum sum = restTemplate.getForObject("http://localhost:" + randomServerPort + "/sum?arg1=8&arg2=4", Sum.class);
        
        assertEquals(1, sum.id());
        assertEquals(String.format(template, 12), sum.content());
    }
    
    @Test
    void should_return_0_plus_4_answer() {
        Sum sum = restTemplate.getForObject("http://localhost:" + randomServerPort + "/sum?arg2=4", Sum.class);
        
        assertEquals(2, sum.id());
        assertEquals(String.format(template, 4), sum.content());
    }
    
    @Test
    void should_return_8_plus_0_answer() {
        Sum sum = restTemplate.getForObject("http://localhost:" + randomServerPort + "/sum?arg1=8", Sum.class);
        
        assertEquals(3, sum.id());
        assertEquals(String.format(template, 8), sum.content());
    }
}
