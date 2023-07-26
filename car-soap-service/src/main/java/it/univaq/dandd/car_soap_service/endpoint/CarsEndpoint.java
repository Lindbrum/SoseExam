package it.univaq.dandd.car_soap_service.endpoint;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.fasterxml.jackson.databind.JsonNode;

import it.univaq.dandd.car_soap_service.feign.ItineraryFeignClient;
import it.univaq.dandd.car_soap_service.model.CarInfo;
import it.univaq.dandd.car_soap_service.service.CarInfoService;
import it.univaq.dandd.car_soap_service.service.CarInfoServiceImpl;
import it.univaq.dandd.wsdltypes.AllCarsResponse;
import it.univaq.dandd.wsdltypes.BookCar;
import it.univaq.dandd.wsdltypes.BookCarResponse;
import it.univaq.dandd.wsdltypes.BookingOperationResult;
import it.univaq.dandd.wsdltypes.CarData;
import it.univaq.dandd.wsdltypes.CarDataList;
import it.univaq.dandd.wsdltypes.FindCar;
import it.univaq.dandd.wsdltypes.FindCarResponse;
import it.univaq.dandd.wsdltypes.FreeCar;
import it.univaq.dandd.wsdltypes.FreeCarResponse;
import it.univaq.dandd.wsdltypes.ObjectFactory;
import it.univaq.dandd.wsdltypes.SearchCars;
import it.univaq.dandd.wsdltypes.SearchCarsResponse;

@Endpoint
public class CarsEndpoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(CarsEndpoint.class);
	
	private final CarInfoService carInfoService;
	private final ItineraryFeignClient itineraryFeignClient;
	
	public CarsEndpoint(CarInfoServiceImpl carInfoServiceImpl, ItineraryFeignClient itineraryFeignClient) {
		this.carInfoService = carInfoServiceImpl;
		this.itineraryFeignClient = itineraryFeignClient;
	}

	@PayloadRoot(namespace = "http://univaq.it/dandd/wsdltypes", localPart = "allCars")
	@ResponsePayload
	public AllCarsResponse getAllCars() {

		LOGGER.info("**** 'CarsEndpoint' RECEIVED A REQUEST FOR 'getAllCars()'");


		//Build response object
		ObjectFactory factory = new ObjectFactory();
		AllCarsResponse response = factory.createAllCarsResponse();
		
		//get the cars
		List<CarInfo> res = carInfoService.getAll();
		//Convert to the XML type (CarData)
		List<CarData> xmlRes = convertCarEntitiesToXML(factory, res);
		//Populate the xml list type		
		CarDataList xmlList = factory.createCarDataList();
		xmlList.getCarData().addAll(xmlRes);
		//Set the response payload
		response.setReturn(xmlList);

		LOGGER.info("**** 'CarsEndpoint' IS GOING TO SEND THE RESULT OF THE 'getAllCars()' OPERATION ='{}'",
				response.getReturn());

		return response;
	}
	
	@PayloadRoot(namespace = "http://univaq.it/dandd/wsdltypes", localPart = "searchCars")
	@ResponsePayload
	public SearchCarsResponse searchCars(@RequestPayload SearchCars req, @Value("${microservice.itinerary.uri}") String itineraryServiceBaseURI) {

		//TODO remove locationName from WSDL (we query it to itinerary prosumer)
		LOGGER.info("**** 'CarsEndpoint' RECEIVED A REQUEST FOR 'searchCars(location={}, name={})'",req.getLocationName(), req.getCarName().getValue());
		
		//Query the current location to the itinerary prosumer
		LOGGER.info("**** Requesting current location to itinerary service...");
		JsonNode locationJson = itineraryFeignClient.getCurrentLocation(); //perform REST GET request
		//Check if it worked
		Optional.ofNullable(locationJson).orElseThrow(); //NoSuchElementException
		//Check if the json reports an error
		String prettyPrinted = locationJson.toPrettyString();
		if(locationJson.has(ItineraryFeignClient.ERROR_STATUS_PROPERTY)) {
			//This will return "Unexpected error" to the client.
			LOGGER.error("****Itinerary prosumer answered with an error: \n{}", prettyPrinted);
			throw new RuntimeException(prettyPrinted);
		}
		LOGGER.info("****Itinerary prosumer answered with the location: \n{}", prettyPrinted);
		//Fetch the property from JSON
		String locationName = locationJson.findValue(ItineraryFeignClient.LOCATION_PROPERTY).asText();
		
		//Build response object
		ObjectFactory factory = new ObjectFactory();
		SearchCarsResponse response = factory.createSearchCarsResponse();
		
		//get the cars from datasource
		List<CarInfo> res = carInfoService.find(locationName, req.getCarName().getValue());
		//Convert to the XML type (CarData)
		List<CarData> xmlRes = convertCarEntitiesToXML(factory, res);
		//Populate the xml list type		
		CarDataList xmlList = factory.createCarDataList();
		xmlList.getCarData().addAll(xmlRes);
		//Set the response payload
		response.setReturn(xmlList);

		LOGGER.info("**** 'CarsEndpoint' IS GOING TO SEND THE RESULT OF THE 'searchCars()' OPERATION =\n{}",
				response.getReturn());

		return response;
	}
	
	@PayloadRoot(namespace = "http://univaq.it/dandd/wsdltypes", localPart = "findCar")
	@ResponsePayload
	public FindCarResponse findCar(@RequestPayload FindCar req) {

		LOGGER.info("**** 'CarsEndpoint' RECEIVED A REQUEST FOR 'FindCar(id={})'",req.getId());


		//Build response object
		ObjectFactory factory = new ObjectFactory();
		FindCarResponse response = factory.createFindCarResponse();
		
		//get the car
		CarInfo res = carInfoService.findById(req.getId());
		//Convert to the XML type (CarData)
		CarData xmlRes = convertFromCarEntityToXML(factory, res);
		
		//Set the response payload
		response.setReturn(xmlRes);

		LOGGER.info("**** 'CarsEndpoint' IS GOING TO SEND THE RESULT OF THE 'findCar()' OPERATION ='{}'",
				response.getReturn());

		return response;
	}
	
	@PayloadRoot(namespace = "http://univaq.it/dandd/wsdltypes", localPart = "bookCar")
	@ResponsePayload
	public BookCarResponse bookCar(@RequestPayload BookCar req) {

		LOGGER.info("**** 'CarsEndpoint' RECEIVED A REQUEST FOR 'bookCar(id={}, until={})'",req.getId(), req.getUntil());


		//Build response object
		ObjectFactory factory = new ObjectFactory();
		BookCarResponse response = factory.createBookCarResponse();
		
		//Convert XMLGregorianCalendar to LocalDateTime
		ZonedDateTime utcZoned = req.getUntil().toGregorianCalendar().toZonedDateTime().withZoneSameInstant(ZoneId.of("UTC"));
		LocalDateTime rentedUntil = utcZoned.toLocalDateTime();
		
		//book the car
		CarInfo res = carInfoService.registerBooking(req.getId(), rentedUntil);
		//Prepare the response
		BookingOperationResult result = factory.createBookingOperationResult();
		result.setCode(HttpStatus.OK.value());
		result.setMessage(HttpStatus.OK.name());
		
		//Set the response payload
		response.setReturn(result);

		LOGGER.info("**** 'CarsEndpoint' IS GOING TO SEND THE RESULT OF THE 'bookCar()' OPERATION ='{}'",
				response.getReturn());

		return response;
	}
	
	@PayloadRoot(namespace = "http://univaq.it/dandd/wsdltypes", localPart = "freeCar")
	@ResponsePayload
	public FreeCarResponse freeCar(@RequestPayload FreeCar req) {

		LOGGER.info("**** 'CarsEndpoint' RECEIVED A REQUEST FOR 'freeCar(id={})'",req.getId());


		//Build response object
		ObjectFactory factory = new ObjectFactory();
		FreeCarResponse response = factory.createFreeCarResponse();
		
		//free the car
		CarInfo res = carInfoService.removeBooking(req.getId());
		//Prepare the response
		BookingOperationResult result = factory.createBookingOperationResult();
		result.setCode(HttpStatus.OK.value());
		result.setMessage(HttpStatus.OK.name());
		
		//Set the response payload
		response.setReturn(result);

		LOGGER.info("**** 'CarsEndpoint' IS GOING TO SEND THE RESULT OF THE 'freeCar()' OPERATION ='{}'",
				response.getReturn());

		return response;
	}

	private List<CarData> convertCarEntitiesToXML(ObjectFactory factory, List<CarInfo> res) {
		return res.stream()
				.filter(car -> car != null)
				.map(car -> convertFromCarEntityToXML(factory, car))
				.collect(Collectors.toList());
	}

	private CarData convertFromCarEntityToXML(ObjectFactory factory, CarInfo car) {
		//create the instance and set all fields
		CarData xmlCar = factory.createCarData();
		xmlCar.setId(car.getId());
		xmlCar.setName(car.getName());
		xmlCar.setPrice(car.getPrice());
		xmlCar.setLocationName(car.getLocationName());
		xmlCar.setRentalLatitude(car.getRentalLatitude());
		xmlCar.setRentalLongitude(car.getRentalLongitude());
		//Convert LocalDateTime to XMLGregorianCalendar
		LocalDateTime UTCTime = car.getAvailableFrom();
		String iso = UTCTime.toString();
		if (UTCTime.getSecond() == 0 && UTCTime.getNano() == 0) {
		    iso += ":00"; // necessary hack because seconds is not optional in XML
		}
		XMLGregorianCalendar xml;
		try {
			xml = DatatypeFactory.newInstance().newXMLGregorianCalendar(iso);
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);	
		}
		xmlCar.setAvailableFrom(xml);
		return xmlCar;
	}
}
