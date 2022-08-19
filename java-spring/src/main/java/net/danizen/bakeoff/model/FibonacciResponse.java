package net.danizen.bakeoff.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class FibonacciResponse {
	private @Getter @Setter @Min(1) @NotNull Integer result;

	public FibonacciResponse(int result) {
	    this.result = result;
	}
	public FibonacciResponse() {
	}
}
