package it.univaq.dandd.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import it.univaq.dandd.model.ItineraryInfo;
import it.univaq.dandd.service.ItineraryService;
import it.univaq.dandd.service.ItineraryServiceImpl;

@RestController
@RequestMapping("/itinerary")
public class ItineraryController {

    // Service (injected)
    private final ItineraryService itineraryService;

    //@Autowired is inferred by Spring Boot when there is a single public constructor
    //@Autowired
    public ItineraryController(ItineraryServiceImpl itineraryServiceImpl) {
        this.itineraryService = itineraryServiceImpl;
    }

    @Operation(summary = "Return all itinerary bookings.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "A JSON array with all itinerary bookings.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ItineraryInfo.class))
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
    public ResponseEntity<List<ItineraryInfo>> findAllBookings() {
        return new ResponseEntity<>(itineraryService.findAllBookings(), HttpStatus.OK);
    }

    @Operation(summary = "Return information about the original info on the booked itineraries.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "A JSON array with information about the original info on the booked itineraries.",
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
    @GetMapping("/all_original/")
    public ResponseEntity<List<Map<String, Object>>> find_original_booking_info() {
        return new ResponseEntity<>(itineraryService.find_Original_Booking_Info(), HttpStatus.OK);
    }

    @Operation(summary = "Return information about the last itinerary record.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "A JSON representation of the last itinerary record.",
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
    @GetMapping("/get_last/")
    public ResponseEntity<Map<String, Object>> find_last() {
        return new ResponseEntity<>(itineraryService.find_last(), HttpStatus.OK);
    }

    @Operation(summary = "Return the next options for the itinerary.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "A JSON array with the next itinerary options depending on the last location recorded.",
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
    @GetMapping("/next_option/")
    public ResponseEntity<List<Map<String, Object>>> next_opt() {
        return new ResponseEntity<>(itineraryService.show_next_option(), HttpStatus.OK);
    }
    
    @Operation(summary = "New record on the itinerary.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Permits to record new steps on the itinerary if among the next options.",
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
    
    
    
    @GetMapping("/choose_next_option/{service_id}/{service_type}/")
    public void add_record(@PathVariable("service_id") int service_id,@PathVariable("service_type") String service_type) throws Exception {
    	itineraryService.choose_next_option(service_id, service_type);
    }
    
    @Operation(summary = "Show cities in itinerary.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "A JSON array with the cities in the itinerary.",
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
    
    @GetMapping("/show_cities/")
    public ResponseEntity<String> showCities() {
        return new ResponseEntity<String>(itineraryService.show_all_cities(), HttpStatus.OK);
    }
    
}
