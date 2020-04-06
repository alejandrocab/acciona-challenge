package com.challenge.application.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.application.dto.TweetDto;
import com.challenge.application.services.TwitterService;

@RestController
@RequestMapping("/tweets")
public class TweetController {

	
	private TwitterService service;
	
	public TweetController(TwitterService service) {
		this.service=service;
	}
	
	@GetMapping
	public ResponseEntity<List<TweetDto>> getTweets (){
		List<TweetDto> response = service.getTweets();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/valid")
	public ResponseEntity<List<TweetDto>> getValidTweets (){
		List<TweetDto> response = service.getTweetsValid();
		return ResponseEntity.ok(response);
	}
	
	
	@PatchMapping("/{id}/valid")
	public ResponseEntity<TweetDto> validTweet(@PathVariable Long id){
		TweetDto response = service.validTweet(id);
		return ResponseEntity.ok(response);
	}
}
