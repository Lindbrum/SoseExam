package it.univaq.dandd.car_soap_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ItineraryLocationInfo(String name) {

}
