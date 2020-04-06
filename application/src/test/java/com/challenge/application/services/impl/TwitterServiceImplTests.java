package com.challenge.application.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.challenge.application.dto.TweetDto;
import com.challenge.application.entities.TweetEntity;
import com.challenge.application.mappers.TweetMapper;
import com.challenge.application.repository.TweetRepository;

import twitter4j.TwitterException;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceImplTests {

	@InjectMocks
	private TwitterServiceImpl service;
	
	@Mock
	private TweetMapper mapper;
	@Mock
	private TweetRepository repository;
	
	@Test
	public void whenGetTweetsThenReturnListTweetDto() {
		List<TweetEntity> entitiesFound = Arrays.asList(TweetEntity.builder().build());
		when(repository.findAll()).thenReturn(entitiesFound);
		when(mapper.entity2Dto(Mockito.any(TweetEntity.class))).thenReturn(Mockito.mock(TweetDto.class));
		List<TweetDto> response =  service.getTweets();
		assertEquals(entitiesFound.size(), response.size());
	}
	
	@Test
	public void whenGetTweetsValidThenReturnListTweetDto() {
		List<TweetEntity> entitiesFound = Arrays.asList(TweetEntity.builder().build());
		when(repository.findAllByValid(true)).thenReturn(entitiesFound);
		when(mapper.entity2Dto(Mockito.any(TweetEntity.class))).thenReturn(Mockito.mock(TweetDto.class));
		List<TweetDto> response =  service.getTweetsValid();
		assertEquals(entitiesFound.size(), response.size());
	}
	
	@Test
	public void whenValidNotFoundTweetThenReturnRuntimeException() {
		Long idNotFound=0L;
		when(repository.findById(idNotFound)).thenThrow(RuntimeException.class);
		assertThrows(RuntimeException.class, ()->service.validTweet(idNotFound));
	}
	
	@Test
	public void whenValidFoundTweetThenReturnTweetValid() {
		Long idFound=1L;
		TweetDto expected = TweetDto.builder().id(idFound).valid(true).build();
		when(repository.findById(idFound)).thenReturn(Optional.of(Mockito.mock(TweetEntity.class)));
		when(mapper.entity2Dto(Mockito.any(TweetEntity.class))).thenReturn(expected);
		TweetDto response = service.validTweet(idFound);
		assertEquals(expected.getId(), response.getId());
		assertEquals(expected.isValid(), response.isValid());
	}
	
	@Test
	public void whenSizeIs10ThenReturn10Trends() throws TwitterException {
		int expected = 10;
		List<String> response = service.getTrends(expected);
		assertEquals(expected, response.size());
	}

}
