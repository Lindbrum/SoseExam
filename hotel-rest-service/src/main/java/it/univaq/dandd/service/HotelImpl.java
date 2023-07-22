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

	@Override
	public List<HotelSchema> findHotelByLocation(String location) {
		return hotelRepository.findAllbyLocatioNameOrderByLocationName(location);
	}

	@Override
	public List<HotelSchema> findHotelByName(String name) { //più di un hotel con lo stesso nome?
		return hotelRepository.findAllbyHotelNameOrderByHotelName(name);
	}

	@Override
	public HotelSchema findHotelById(long id) throws HotelNotFoundException {
		return hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel with id = "+id+" not found."));
	}

}
