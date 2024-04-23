package com.example.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Entity.Tweet;

@Service
public interface TweetRedisService {

	List<Tweet> getAllTweetsByUserIdFromCache(String userId);

	void cacheAllTweetsByUserId(String userId, List<Tweet> tweets);
	

}
