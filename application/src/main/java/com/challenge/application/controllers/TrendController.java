package com.challenge.application.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.application.services.TwitterService;

import twitter4j.TwitterException;

@RestController
@RequestMapping("/trends")
public class TrendController {
	
	private TwitterService service;
	
	public TrendController(TwitterService service) {
		this.service=service;
	}

	@GetMapping()
	public ResponseEntity<List<String>> getTrends (@RequestParam(name="size", required=false, defaultValue="10") int size) throws TwitterException{
		List<String> response = service.getTrends(size);
		return ResponseEntity.ok(response);
	}
}
