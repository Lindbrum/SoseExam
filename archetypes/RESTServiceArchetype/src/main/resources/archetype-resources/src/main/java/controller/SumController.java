package ${package}.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import ${package}.model.Sum;
import ${package}.service.SumService;
import ${package}.service.SumServiceImpl;

@RestController
public class SumController {
	
	//Service (injected)
	private final SumService sumService;
	
	private final AtomicLong counter = new AtomicLong();
	
	//@Autowired is inferred by Spring Boot when there is a single public constructor
	//@Autowired
	public SumController(SumServiceImpl sumServiceImpl) {
		this.sumService = sumServiceImpl;
	}

	@Operation(summary = "Calculate the sum of two integers and returns a formatted message.")
	@ApiResponses(value = { 
		  @ApiResponse(
				  responseCode = "200", 
				  description = "The sum was calculated and the message template found.", 
				  content = { 
						  @Content(
								  mediaType = "application/json", 
								  schema = @Schema(implementation = Sum.class)
								  ) 
				  }),
		  @ApiResponse(
				  responseCode = "400",
				  description = "Invalid addendum(s) provided", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }), 
		  @ApiResponse(
				  responseCode = "500",
				  description = "Message template not found", 
				  content = {
						  @Content(
								  mediaType = "application/json"
								  )
				  }) 
	})
	@GetMapping("/sum")
	public Sum sum(@RequestParam(value = "arg1", defaultValue = "0") int arg1, 
						@RequestParam(value = "arg2", defaultValue = "0") int arg2) {
		return new Sum(counter.incrementAndGet(), String.format(sumService.getTemplate(), arg1 + arg2));
	}
}
