package com.example.Repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.Entity.Tweet;


@Repository
public interface TweetJdbcRepository {
	List<Tweet> getAllTweetsByUser(Long userId);

	String addTweet(Long userId, Tweet tweet);

	String updateTweet(Long userId, Long tweetId, Tweet tweet);

	String deleteTweet(Long userId, Long TweetId);
}
