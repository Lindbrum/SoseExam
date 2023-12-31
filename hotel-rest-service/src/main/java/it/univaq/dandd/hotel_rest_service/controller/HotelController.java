package it.univaq.dandd.hotel_rest_service.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.univaq.dandd.hotel_rest_service.model.HotelInfo;
import it.univaq.dandd.hotel_rest_service.service.HotelServiceImpl;
import it.univaq.dandd.hotel_rest_service.service.HotelService;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/hotels")
public class HotelController {
	
	//Service (injected)
	private final HotelService hotelService;
	
	
	public HotelController(HotelServiceImpl hotelServiceImpl) {
		this.hotelService = hotelServiceImpl;
	}

	@Operation(summary = "Return all registered hotels.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "A JSON array with all hotels registered.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = HotelInfo.class)
								  ) 
				  }),
		  @ApiResponse(
				  responseCode = "500",
				  description = "Unexpected error occurred.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  })
	})
	@GetMapping("/all/")
	public ResponseEntity<List<HotelInfo>> getAllHotels() {
		return new ResponseEntity<List<HotelInfo>>(hotelService.findAllHotels(), HttpStatus.OK);
	}
	
	@Operation(summary = "Return registered hotels, filtered by name and/or location.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "A JSON array with all hotels registered with a certain name.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = HotelInfo.class)
								  ) 
				  }),
		  @ApiResponse(
				  responseCode = "500",
				  description = "Unexpected error occurred.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  })
	})
	
	@GetMapping("/")
	public ResponseEntity<List<HotelInfo>> getHotelsByName(@RequestParam(required = false, defaultValue = "") String location, @RequestParam(required = false, defaultValue = "") String name) {
		return new ResponseEntity<List<HotelInfo>>(hotelService.findSpecificHotels(location,name), HttpStatus.OK);
	}
	
	
	
	@Operation(summary = "Return the hotel with this id.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "A JSON representing this hotel.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = HotelInfo.class)
								  ) 
				  }),
		  @ApiResponse(
				  responseCode = "400",
				  description = "The ID path parameter is invalid.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }),
		  @ApiResponse(
				  responseCode = "404",
				  description = "Hotel not found for this ID.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }),
		  @ApiResponse(
				  responseCode = "500",
				  description = "Unexpected error occurred.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  })
	})
	
	@GetMapping("/{id}/")
	public ResponseEntity<HotelInfo> findHotel(@PathVariable(value = "id") long id) {
		System.out.println(hotelService.findHotelById(id));
		return new ResponseEntity<HotelInfo>(hotelService.findHotelById(id), HttpStatus.OK);
	}
	
	
}
