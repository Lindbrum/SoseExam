package it.univaq.dandd.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.univaq.dandd.model.FlightRoute;
import it.univaq.dandd.service.FlightRouteService;
import jakarta.websocket.server.PathParam;
import it.univaq.dandd.service.FlightRouteImpl;

@RestController
@RequestMapping("/flights")
public class FlightController {
	
	//Service (injected)
	private final FlightRouteService flightRouteService;
	
	
	public FlightController(FlightRouteImpl flightRouteImpl) {
		this.flightRouteService = flightRouteImpl;
	}

	@Operation(summary = "Return all future flight routes, eventually filtered by departing and/or arrriving city.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "A JSON array with all matching future routes.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  array = @ArraySchema(schema = @Schema(implementation = FlightRoute.class))
								  ) 
				  }), 
		  @ApiResponse(
				  responseCode = "500",
				  description = "Unexpected error occured.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }) 
	})
	@GetMapping("/")
	public ResponseEntity<List<FlightRoute>> findRoutes(@RequestParam(required = false,defaultValue = "") String departure,@RequestParam(required = false, defaultValue = "") String arrival){
		return new ResponseEntity<List<FlightRoute>>(flightRouteService.findFutureRoutes(departure, arrival), HttpStatus.OK);
	};
	
	@Operation(summary = "Return a route matching this id.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "A JSON representing this flight route.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  array = @ArraySchema(schema = @Schema(implementation = FlightRoute.class))
								  ) 
				  }),
		  @ApiResponse(
				  responseCode = "404",
				  description = "Route not found for this ID.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }),
		  @ApiResponse(
				  responseCode = "500",
				  description = "Unexpected error occured.", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }) 
	})
	
	@GetMapping("/{id}/")
	public ResponseEntity<FlightRoute> findRoute(@PathParam(value = "id") Long id){
		return new ResponseEntity<FlightRoute>(flightRouteService.findRouteById(id), HttpStatus.OK);
	}
}
