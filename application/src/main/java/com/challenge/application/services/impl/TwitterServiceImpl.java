package com.challenge.application.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.application.dto.TweetDto;
import com.challenge.application.entities.TweetEntity;
import com.challenge.application.mappers.TweetMapper;
import com.challenge.application.repository.TweetRepository;
import com.challenge.application.services.TwitterService;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@Service
public class TwitterServiceImpl implements TwitterService{

	
	private Twitter twitter;
	private TweetRepository repository;
	private TweetMapper mapper;
	
	@Value("${custom.tweet.langs}")
	private List<String> langs;
	
	@Value("${custom.tweet.user.followers}")
	private int followers;
	
	public TwitterServiceImpl(TweetRepository repository, TweetMapper mapper) {
		this.twitter = TwitterFactory.getSingleton();
		this.repository=repository;
		this.mapper=mapper;
	}

	@Override
	@Transactional
	public List<TweetDto> getTweets() {
		return repository.findAll().stream().map(mapper::entity2Dto).collect(Collectors.toList());
	}
	
	@Override
	@Transactional
	public List<TweetDto> getTweetsValid() {
		return repository.findAllByValid(true).stream().map(mapper::entity2Dto).collect(Collectors.toList());
	}
	
	@Override
	@Transactional
	public TweetDto validTweet(Long id) {
		TweetEntity entity = repository.findById(id).orElseThrow(()-> new RuntimeException("Tweet with id: "+id+" not exists"));
		entity.setValid(true);
		repository.save(entity);
		return mapper.entity2Dto(entity);
	}
	
	@Override
	public List<String> getTrends(int size) throws TwitterException {
		return Arrays.stream(twitter.trends().getPlaceTrends(1).getTrends()).limit(size).map(trend->trend.getName()).collect(Collectors.toList());
	}	
	
	@PostConstruct
	private void init () throws TwitterException {
		System.out.println("load tweets with langs: "+langs+" and followers: "+followers);
		twitter.getHomeTimeline().stream()
		.filter(tweet-> tweet.getUser().getFollowersCount()>followers)
		.filter(tweet->langs.contains(tweet.getLang()))
		.map(mapper::tweet2Entity)
		.forEach(repository::save);
		System.out.println("::tweets loaded successfully");
	}
}
