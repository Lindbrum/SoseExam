package it.univaq.dandd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.univaq.dandd.dao.HotelRepository;
import it.univaq.dandd.exception.HotelNotFoundException;
import it.univaq.dandd.model.HotelSchema;

@Service
public class HotelImpl implements HotelService {

	private final HotelRepository hotelRepository;
	
	public HotelImpl(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	@Override
	public List<HotelSchema> findAllHotels() {
		return hotelRepository.findAll();
	}
	
	public List<HotelSchema> findSpecificHotels(String location, String name) {
		//Count how many parameters are set, converting the Strings to lowercase
		int parametersSet = 0;
		//departing city
		if(location!=null && !location.isBlank()) {
			location = location.toLowerCase();
			parametersSet++;
		}
		//arriving city
		if(name!=null && !name.isBlank()) {
			name = name.toLowerCase();
			parametersSet++;
		}
		
		List<HotelSchema> result;
		
		switch (parametersSet) {
		case 1: {
			//filter either by departure or arrival
			if(location!=null && !location.isBlank()) {
				//filter by departure
				result = hotelRepository.findAllByLocationNameOrderByLocationName(location);
			}else {
				//filter by arrival
				result = hotelRepository.findAllByHotelNameOrderByHotelName(name);
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + parametersSet);
		}
		
		return result;
		}




	@Override
	public HotelSchema findHotelById(long id) throws HotelNotFoundException {
		return hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel with id = "+id+" not found."));
	}

}
