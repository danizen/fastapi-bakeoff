package net.danizen.bakeoff;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.danizen.bakeoff.model.FibonacciResponse;
import net.danizen.bakeoff.service.FibonacciService;

public class TestFibonacciService {

	private FibonacciService service;

	@BeforeEach
	public void setup() {
		service = new FibonacciService();
	}

	@Test
	public void baseCase0() {
		var expected = new FibonacciResponse(1);
		var actual = service.getFibonacci(0);
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void baseCase1() {
		var expected = new FibonacciResponse(1);
		var actual = service.getFibonacci(1);
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void case8() {
		var expected = new FibonacciResponse(34);
		var actual = service.getFibonacci(8);
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void case34() {
		var expected = new FibonacciResponse(9227465);
		var actual = service.getFibonacci(34);
		assertThat(actual).isEqualTo(expected);
	}
}
