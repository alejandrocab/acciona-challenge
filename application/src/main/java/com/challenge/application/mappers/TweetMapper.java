package com.challenge.application.mappers;

import org.springframework.stereotype.Component;

import com.challenge.application.dto.TweetDto;
import com.challenge.application.entities.TweetEntity;

import twitter4j.Status;

@Component
public class TweetMapper {

	public TweetEntity tweet2Entity (Status tweet) {
		return TweetEntity.builder()
				.user(tweet.getUser().getName())
				.text(tweet.getText())
				.location(tweet.getUser().getLocation())
				.valid(false)
				.build();
	}
	
	public TweetDto entity2Dto (TweetEntity entity) {
		return TweetDto.builder()
				.id(entity.getId())
				.user(entity.getUser())
				.text(entity.getText())
				.location(entity.getLocation())
				.valid(entity.isValid())
				.build();
	}
}
