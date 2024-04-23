package com.example.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Entity.Tweet;

@Service
public interface TweetService {
	List<Tweet> getAllTweetsByUser(Long userId);

	String addTweet(Long userId, Tweet tweet);

	String updateTweet(Long userId, Long tweetId, Tweet tweet);

	String deleteTweet(long userId, Long TweetId);
}
