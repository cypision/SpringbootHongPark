package com.example.fristspring.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Article {

    @Id // This means Primary Key but Id doesnt mattter DB Table Primary key setting!
    @GeneratedValue // 1,2,3,4
    private Long id;

    private String title;

    private String content;

}

