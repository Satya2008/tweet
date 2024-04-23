package com.example.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Entity.Tweet;
import com.example.Repository.TweetJdbcRepository;

@Service
public class TweetServiceImpl implements TweetService {

	@Autowired
	private TweetJdbcRepository tweetJdbcRepository;

	@Autowired
	private TweetRedisService tweetRedisService;

	 @Override
	    public List<Tweet> getAllTweetsByUser(Long userId) {
	        List<Tweet> tweets = tweetRedisService.getAllTweetsByUserIdFromCache(String.valueOf(userId));
	        if (tweets == null) {
	            tweets = tweetJdbcRepository.getAllTweetsByUser(userId);
	            tweetRedisService.cacheAllTweetsByUserId(String.valueOf(userId), tweets);
	        }
	        return tweets;
	    }

	    @Override
	    public String addTweet(Long userId, Tweet tweet) {
	        String result = tweetJdbcRepository.addTweet(userId, tweet);
	        if (result.equals("Tweet added successfully")) {
	            List<Tweet> tweets = getAllTweetsByUser(userId);
	            tweets.add(tweet);
	            tweetRedisService.cacheAllTweetsByUserId(String.valueOf(userId), tweets);
	        }
	        return result;
	    }

	    @Override
	    public String updateTweet(Long userId, Long tweetId, Tweet tweet) {
	        String result = tweetJdbcRepository.updateTweet(userId, tweetId, tweet);
	        if (result.equals("Tweet updated successfully")) {
	            List<Tweet> tweets = getAllTweetsByUser(userId);
	            for (int i = 0; i < tweets.size(); i++) {
	                if (tweets.get(i).getId().equals(tweetId)) {
	                    tweets.set(i, tweet);
	                    break;
	                }
	            }
	            tweetRedisService.cacheAllTweetsByUserId(String.valueOf(userId), tweets);
	        }
	        return result;
	    }

	    @Override
	    public String deleteTweet(long userId, Long tweetId) {
	        String result = tweetJdbcRepository.deleteTweet(userId, tweetId);
	        if (result.equals("Tweet deleted successfully")) {
	            List<Tweet> tweets = getAllTweetsByUser(userId);
	            for (int i = 0; i < tweets.size(); i++) {
	                if (tweets.get(i).getId().equals(tweetId)) {
	                    tweets.remove(i);
	                    break;
	                }
	            }
	            tweetRedisService.cacheAllTweetsByUserId(String.valueOf(userId), tweets);
	        }
	        return result;
	    }
}
