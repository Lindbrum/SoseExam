package it.univaq.dandd.boat_rest_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import it.univaq.dandd.boat_rest_service.model.NauticalRoute;
import it.univaq.dandd.boat_rest_service.service.NauticalRouteService;
import it.univaq.dandd.boat_rest_service.service.NauticalRouteServiceImpl;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/boats")
public class BoatController {
	
	//Service (injected)
	private final NauticalRouteService nauticalRouteService;
	
	//@Autowired is inferred by Spring Boot when there is a single public constructor
	//@Autowired
	public BoatController(NauticalRouteServiceImpl nauticalRouteServiceImpl) {
		this.nauticalRouteService = nauticalRouteServiceImpl;
	}

	@Operation(summary = "Return all nautical routes currently registered.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "A JSON array with all nautical routes.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  array = @ArraySchema(schema = @Schema(implementation = NauticalRoute.class))
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
	@GetMapping("/all/")
	public ResponseEntity<List<NauticalRoute>> getAllRoutes() {
		return new ResponseEntity<List<NauticalRoute>>(nauticalRouteService.findAllRoutes(), HttpStatus.OK);
	}
	
	@Operation(summary = "Return all future nautical routes, eventually filtered by departing and/or arrriving city.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "A JSON array with all matching future routes.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  array = @ArraySchema(schema = @Schema(implementation = NauticalRoute.class))
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
	public ResponseEntity<List<NauticalRoute>> findRoutes(@RequestParam(required = false, defaultValue = "") String departure, @RequestParam(required = false, defaultValue = "") String arrival) {
		return new ResponseEntity<List<NauticalRoute>>(nauticalRouteService.findFutureRoutes(departure, arrival), HttpStatus.OK);
	}
	
	@Operation(summary = "Return a route matching this id.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "A JSON representing this nautical route.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  array = @ArraySchema(schema = @Schema(implementation = NauticalRoute.class))
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
	public ResponseEntity<NauticalRoute> findRoute(@PathParam(value = "id") long id) {
		return new ResponseEntity<NauticalRoute>(nauticalRouteService.findRouteById(id), HttpStatus.OK);
	}
}
