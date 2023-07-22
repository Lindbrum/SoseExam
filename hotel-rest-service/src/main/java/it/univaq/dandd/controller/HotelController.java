package it.univaq.dandd.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.univaq.dandd.model.HotelSchema;
import it.univaq.dandd.service.HotelImpl;
import it.univaq.dandd.service.HotelService;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/boats")
public class HotelController {
	
	//Service (injected)
	private final HotelService hotelService;
	
	
	public HotelController(HotelImpl hotelImpl) {
		this.hotelService = hotelImpl;
	}

	@Operation(summary = "Return all registered hotels.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "A JSON array with all hotels registered.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = HotelSchema.class)
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
	public ResponseEntity<List<HotelSchema>> getAllHotels() {
		return new ResponseEntity<List<HotelSchema>>(hotelService.findAllHotels(), HttpStatus.OK);
	}
	
	@Operation(summary = "Return registered hotels with a certain name.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "A JSON array with all hotels registered with a certain anme.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = HotelSchema.class)
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
	
	@GetMapping("/name")
	public ResponseEntity<List<HotelSchema>> getHotelsByName(@RequestParam(required = false, defaultValue = "") String name) {
		return new ResponseEntity<List<HotelSchema>>(hotelService.findHotelByName(name), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Return registered hotels in a certain location.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "A JSON array with all hotels registered in a certain location.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = HotelSchema.class)
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
	
	@GetMapping("/location")
	public ResponseEntity<List<HotelSchema>> getHotelsByLocation(@RequestParam(required = false, defaultValue = "") String location) {
		return new ResponseEntity<List<HotelSchema>>(hotelService.findHotelByLocation(location), HttpStatus.OK);
	}
	
	
	@Operation(summary = "Return the hotel with this id.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "A JSON array representing this hotel.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = HotelSchema.class)
								  ) 
				  }),
		  @ApiResponse(
				  responseCode = "404",
				  description = "Hotel not found.", 
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
	public ResponseEntity<HotelSchema> findHotel(@PathParam(value = "id") long id) {
		return new ResponseEntity<HotelSchema>(hotelService.findHotelById(id), HttpStatus.OK);
	}
	
	
}
