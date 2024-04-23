package com.example.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.Entity.Tweet;
import redis.clients.jedis.Jedis;

@Service
public class TweetRedisServiceImpl implements TweetRedisService {
	private String REDIS_HOST = "localhost";
	private int REDIS_PORT = 6379;

	@Override
	public List<Tweet> getAllTweetsByUserIdFromCache(String userId) {
		try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
			String serializedTweets = jedis.get(userId);
			return deserializeTweets(serializedTweets);
		}
	}

	@Override
	public void cacheAllTweetsByUserId(String userId, List<Tweet> tweets) {
		try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
			String serializedTweets = serializeTweets(tweets);
			jedis.set(userId, serializedTweets);
		}
	}

	private String serializeTweets(List<Tweet> tweets) {
		return tweets.stream()
				.map(tweet -> tweet.getId() + ":" + tweet.getContent() + ":" + tweet.getAuthor() + ":" + tweet.getCreatedAt())
				.collect(Collectors.joining(","));
	}

	private List<Tweet> deserializeTweets(String serializedTweets) {
		return List.of(serializedTweets.split(",")).stream()
				.map(tweetStr -> {
					String[] parts = tweetStr.split(":");
					if (parts.length == 5) {
						Long id = Long.parseLong(parts[0]);
						String content = parts[1];
						String author = parts[2];
						Date createdAt = Date.valueOf(parts[3]);
						return new Tweet(content, author, createdAt, id);
					}
					return null;
				})
				.filter(tweet -> tweet != null)
				.collect(Collectors.toList());
	}
}
