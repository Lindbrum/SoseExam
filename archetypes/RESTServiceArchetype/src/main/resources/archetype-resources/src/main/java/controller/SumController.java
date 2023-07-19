package ${package}.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ${package}.model.Sum;
import ${package}.service.SumService;
import ${package}.service.SumServiceImpl;

@RestController
public class SumController {
	
	//Service (injected)
	private final SumService sumService;
	
	private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	public SumController(SumServiceImpl sumServiceImpl) {
		this.sumService = sumServiceImpl;
	}

	@GetMapping("/sum")
	public Sum sum(@RequestParam(value = "arg1", defaultValue = "0") int arg1, 
						@RequestParam(value = "arg2", defaultValue = "0") int arg2) {
		return new Sum(counter.incrementAndGet(), String.format(sumService.getTemplate(), arg1 + arg2));
	}
}
