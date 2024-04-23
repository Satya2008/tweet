package com.example.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.Entity.Tweet;


@Repository
public class TweetJdbcRepositoryImpl implements TweetJdbcRepository {

    private Connection connection;
    
    public TweetJdbcRepositoryImpl() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tweet", "root", "root@root");
    }

    @Override
    public List<Tweet> getAllTweetsByUser(Long userId) {
        List<Tweet> tweets = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tweets WHERE user_id = ?");
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tweet tweet = new Tweet();
                tweet.setId(resultSet.getLong("id"));
                tweet.setContent(resultSet.getString("content"));
                tweet.setAuthor(resultSet.getString("author"));
                tweet.setCreatedAt(resultSet.getDate("created_at"));
                tweets.add(tweet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tweets;
    }

    @Override
    public String addTweet(Long userId, Tweet tweet) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tweets (content, author, created_at, user_id) VALUES (?, ?, ?, ?)");
            statement.setString(1, tweet.getContent());
            statement.setString(2, tweet.getAuthor());
            statement.setDate(3, new java.sql.Date(tweet.getCreatedAt().getTime()));
            statement.setLong(4, userId);
            statement.executeUpdate();
            return "Tweet added successfully";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to add tweet";
        }
    }

    @Override
    public String updateTweet(Long userId, Long tweetId, Tweet tweet) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE tweets SET content = ?, author = ?, created_at = ? WHERE id = ? AND user_id = ?");
            statement.setString(1, tweet.getContent());
            statement.setString(2, tweet.getAuthor());
            statement.setDate(3, new java.sql.Date(tweet.getCreatedAt().getTime()));
            statement.setLong(4, tweetId);
            statement.setLong(5, userId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return "Tweet updated successfully";
            } else {
                return "Tweet not found to update";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to update tweet";
        }
    }

    @Override
    public String deleteTweet(Long userId, Long tweetId) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM tweets WHERE id = ? AND user_id = ?");
            statement.setLong(1, tweetId);
            statement.setLong(2, userId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                return "Tweet deleted successfully";
            } else {
                return "Tweet not found to delete";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to delete tweet";
        }
    }

	
}
