package it.univaq.dandd.car_soap_service.endpoint;

import javax.xml.namespace.QName;
import javax.xml.transform.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;

import it.univaq.dandd.car_soap_service.exception.CarNotFoundException;
import it.univaq.dandd.car_soap_service.exception.IllegalDateException;
import it.univaq.dandd.wsdltypes.ExceptionData;
import it.univaq.dandd.wsdltypes.ObjectFactory;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

/**
 * Custom extension of the soap fault mapping exception resolver. This will add a fault detail element that contains:
 * 1. Description of the HTTP error
 * 2. Code of the HTTP error
 * 3. the exception message
 */
public class CustomSoapFaultMappingExceptionResolver extends SoapFaultMappingExceptionResolver {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomSoapFaultMappingExceptionResolver.class);
	

	private static final QName STATUS = new QName("status");
	private static final QName CODE = new QName("code");
	private static final QName MESSAGE = new QName("message");
	
	private static final ObjectFactory FACTORY = new ObjectFactory();
	  private final Marshaller marshaller;

	  public CustomSoapFaultMappingExceptionResolver() throws JAXBException {
		  LOGGER.info("Customer Resolver set");
	    JAXBContext jaxbContext = JAXBContext.newInstance(ExceptionData.class);
	    this.marshaller = jaxbContext.createMarshaller();
	  }
	

	@Override
	protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
		LOGGER.info("customizeFault() called");
		//Define default fault
		SoapFaultDefinition faultDefinition = new SoapFaultDefinition();
		faultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
		faultDefinition.setFaultStringOrReason("An error occured.");
		setDefaultFault(faultDefinition);
		
		ExceptionData exceptionData = FACTORY.createExceptionData();
		//404 - Not found
		if(ex instanceof CarNotFoundException) {
			//Set fault message
//			detail.addFaultDetailElement(STATUS).addText(HttpStatus.NOT_FOUND.name());			
//			detail.addFaultDetailElement(CODE).addText(""+HttpStatus.NOT_FOUND.value());
//			detail.addFaultDetailElement(MESSAGE).addText(ex.getLocalizedMessage());
			exceptionData.setStatus(HttpStatus.NOT_FOUND.name());
			exceptionData.setCode(HttpStatus.NOT_FOUND.value());
			exceptionData.setMessage(ex.getLocalizedMessage());
		}else if(ex instanceof IllegalDateException) {
			//400 - Bad request
//			detail.addFaultDetailElement(STATUS).addText(HttpStatus.BAD_REQUEST.name());			
//			detail.addFaultDetailElement(CODE).addText(""+HttpStatus.BAD_REQUEST.value());
//			detail.addFaultDetailElement(MESSAGE).addText(ex.getLocalizedMessage());
		}else {
			//500 - Internal server error
//			detail.addFaultDetailElement(STATUS).addText(HttpStatus.INTERNAL_SERVER_ERROR.name());			
//			detail.addFaultDetailElement(CODE).addText(""+HttpStatus.INTERNAL_SERVER_ERROR.value());
//			detail.addFaultDetailElement(MESSAGE).addText("Unexpected error has occured.");
		}
		
		SoapFaultDetail detail = fault.addFaultDetail();
		
		
		//Marshal
		try {
			marshaller.marshal(exceptionData, detail.getResult());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	    
	}

}
