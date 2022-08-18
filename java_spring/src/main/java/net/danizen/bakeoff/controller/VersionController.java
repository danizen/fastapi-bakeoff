package net.danizen.bakeoff.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.danizen.bakeoff.model.VersionResponse;

@RestController("/version")
public class VersionController {

	@GetMapping
	public VersionResponse getVersion() {
		return new VersionResponse("0.0.1");
	}
}
