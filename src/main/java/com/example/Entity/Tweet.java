package com.example.Entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
}