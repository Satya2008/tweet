package com.example.Controller;


import com.example.Entity.Tweet;
import com.example.Service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Tweet>> getAllTweetsByUser(@PathVariable Long userId) {
        List<Tweet> tweets = tweetService.getAllTweetsByUser(userId);
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> addTweet(@PathVariable Long userId, @RequestBody Tweet tweet) {
    	System.out.println(userId);
    	System.out.println(tweet);
        String result = tweetService.addTweet(userId, tweet);
        
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/{tweetId}")
    public ResponseEntity<String> updateTweet(@PathVariable Long userId, @PathVariable Long tweetId, @RequestBody Tweet tweet) {
        String result = tweetService.updateTweet(userId, tweetId, tweet);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{tweetId}")
    public ResponseEntity<String> deleteTweet(@PathVariable Long userId, @PathVariable Long tweetId) {
        String result = tweetService.deleteTweet(userId, tweetId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

