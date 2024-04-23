package com.example.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.Entity.Tweet;

@Service
public class TweetRedisServiceImpl implements TweetRedisService {

	@Autowired
	private  RedisTemplate<String, List<Tweet>> redisTemplate;
	
	    @Override
	    public List<Tweet> getAllTweetsByUserIdFromCache(String userId) {
	        return redisTemplate.opsForValue().get(userId);
	    }

	    @Override
	    public void cacheAllTweetsByUserId(String userId, List<Tweet> tweets) {
	        redisTemplate.opsForValue().set(userId, tweets);
	    }
	    
}
