package net.danizen.bakeoff;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import net.danizen.bakeoff.model.FibonacciResponse;
import net.danizen.bakeoff.model.VersionResponse;
import net.danizen.bakeoff.service.FibonacciService;

@SpringBootApplication
@RestController
@Validated
public class BakeoffApplication {

	@Autowired
	private FibonacciService fibonacciService;
	
	public static void main(String[] args) {
		SpringApplication.run(BakeoffApplication.class, args);
	}

	@GetMapping("/version")
	public VersionResponse getVersion() {
		return new VersionResponse("0.0.1");
	}
	
	@GetMapping("/fibonacci/{number}")
	public FibonacciResponse getFibonacci(@PathVariable @Min(0) @Max(50) int number) {
		return fibonacciService.getFibonacci(number);
	}
}
