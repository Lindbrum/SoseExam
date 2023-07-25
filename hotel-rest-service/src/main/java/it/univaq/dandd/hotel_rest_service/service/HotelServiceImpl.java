package it.univaq.dandd.hotel_rest_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.univaq.dandd.hotel_rest_service.dao.HotelRepository;
import it.univaq.dandd.hotel_rest_service.exception.HotelNotFoundException;
import it.univaq.dandd.hotel_rest_service.model.HotelInfo;

@Service
public class HotelServiceImpl implements HotelService {

	private final HotelRepository hotelRepository;
	
	public HotelServiceImpl(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	@Override
	public List<HotelInfo> findAllHotels() {
		return hotelRepository.findAll();
	}
	
	public List<HotelInfo> findSpecificHotels(String location, String name) {
		//Count how many parameters are set, converting the Strings to lowercase
		int parametersSet = 0;
		//location name
		if(location!=null && !location.isBlank()) {
			location = location.toLowerCase();
			parametersSet++;
		}
		//(partial) hotel name
		if(name!=null && !name.isBlank()) {
			name = name.toLowerCase();
			parametersSet++;
		}
		
		List<HotelInfo> result;
		
		switch (parametersSet) {
		case 0: {
			//no filters
			result = hotelRepository.findAll();
		}
		case 1: {
			//filter either by location or (partial) name
			if(location!=null && !location.isBlank()) {
				//filter by location
				result = hotelRepository.findAllByLocationNameIgnoreCaseOrderByHotelName(location);
			}else {
				//filter by (partial) hotel name
				result = hotelRepository.findAllByHotelNameContainingIgnoreCaseOrderByLocationName(name);
			}
			break;
		}
		case 2: {
			//filter by both location and hotel name
			result = hotelRepository.findAllByLocationNameIgnoreCaseAndHotelNameContainingIgnoreCaseOrderByHotelName(location, name);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + parametersSet);
		}
		
		return result;
		}




	@Override
	public HotelInfo findHotelById(long id) throws HotelNotFoundException {
		return hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel with id = "+id+" not found."));
	}

}
