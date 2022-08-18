package net.danizen.bakeoff.service;

import java.util.Vector;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import net.danizen.bakeoff.model.FibonacciResponse;

@Service
@SessionScope
public class FibonacciService {
	
	private Vector<Integer> cache = new Vector<Integer>(50,5);
	
	public FibonacciService() {
		cache.setSize(2);
		cache.set(0, 1);
		cache.set(1, 1);
	}

	public FibonacciResponse getFibonacci(int number) {
		int result = fib(number);
		return new FibonacciResponse(result);
	}
	
	private int fib(int number) {
		// make sure cache grows for new numbers
		if (cache.size() < number+1) {
			cache.setSize(number+1);
		}
		// if the number is not cached, calculate it
		if (cache.get(number) == null) {
			int result = fib(number - 1) + fib(number - 2);
			cache.set(number, result);
		}
		// return that cached value
		return cache.get(number);
	}
}
