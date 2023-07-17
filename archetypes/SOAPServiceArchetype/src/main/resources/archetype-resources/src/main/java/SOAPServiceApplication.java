package ${package};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SOAPServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SOAPServiceApplication.class, args);
		System.out.println("Please, use the Maven command \"mvn generate-sources\" to generate the service classes package from the wsdl, then past them into the source folder");
	}

}