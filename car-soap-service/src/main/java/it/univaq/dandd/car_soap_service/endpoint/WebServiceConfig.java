package it.univaq.dandd.car_soap_service.endpoint;

import java.util.Properties;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;

import it.univaq.dandd.car_soap_service.exception.CarNotFoundException;
import it.univaq.dandd.car_soap_service.exception.IllegalDateException;
import jakarta.xml.bind.JAXBException;

import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		
		return new ServletRegistrationBean<>(servlet, "/*");
	}
	
	//Wsdl will be exposed at "<host>:<port>/ws/sum.wsdl"
	@Bean(name = "cars")
	public Wsdl11Definition defaultWsdl11Definition() {
		SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
		wsdl11Definition.setWsdl(new ClassPathResource("/wsdl/cars.wsdl"));
		return wsdl11Definition;
	}
	
	//Customize SOAP faults by using a custom resolver
	@Bean
	public SoapFaultMappingExceptionResolver exceptionResolver() throws JAXBException {
		//The default fault is set internally in the custom resolver
		SoapFaultMappingExceptionResolver resolver = new CustomSoapFaultMappingExceptionResolver();
		//Exception mappings
		Properties exceptionMappings = new Properties(2);
		exceptionMappings.setProperty(CarNotFoundException.class.getName(), SoapFaultDefinition.CLIENT.toString());
		exceptionMappings.setProperty(IllegalDateException.class.getName(), SoapFaultDefinition.CLIENT.toString());
		resolver.setExceptionMappings(exceptionMappings);
		resolver.setMappedEndpoints(null);
		
		return resolver;
	}
}