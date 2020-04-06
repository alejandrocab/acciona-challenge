package com.challenge.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.application.entities.TweetEntity;

public interface TweetRepository extends JpaRepository<TweetEntity, Long>{

	public List<TweetEntity> findAllByValid(boolean valid);
}
