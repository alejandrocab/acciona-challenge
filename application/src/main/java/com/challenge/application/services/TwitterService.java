package com.challenge.application.services;

import java.util.List;

import com.challenge.application.dto.TweetDto;

import twitter4j.TwitterException;

public interface TwitterService {
	public List<TweetDto> getTweets();
	public List<TweetDto> getTweetsValid();
	public List<String> getTrends(int size) throws TwitterException;
	public TweetDto validTweet (Long id);
}
