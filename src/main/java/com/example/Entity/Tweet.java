package com.example.Entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tweet {
    private Long id;
    private String content;
    private String author;
    private Date createdAt;
    
   
    private User user;

    public Tweet(String content, String author, Date createdAt, Long id) {
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.id = id;
    }
}