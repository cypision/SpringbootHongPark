package com.example.fristspring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // 해당 class 로 Table을 만든다.
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Article {

    @Id // This means Primary Key but Id doesnt mattter DB Table Primary key setting!
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 전략
    private Long id;

    private String title;

    private String content;


}

