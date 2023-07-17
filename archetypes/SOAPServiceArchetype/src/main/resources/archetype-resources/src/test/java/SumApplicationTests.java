package ${package};

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import ${package}.client.SumClient;


@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SumApplicationTests {

	@Autowired
	private SumClient sumClient;

	@Test
	public void testSum_4_8() {
		Assertions.assertEquals(12, sumClient.sum(4, 8));
	}
	
	@Test
	public void testSum_8_4() {
		Assertions.assertEquals(12, sumClient.sum(8, 4));
	}
}
