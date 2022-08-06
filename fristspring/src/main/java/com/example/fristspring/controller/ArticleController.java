package com.example.fristspring.controller;

import com.example.fristspring.dto.ArticleForm;
import com.example.fristspring.entity.Article;
import com.example.fristspring.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j // logging
public class ArticleController {

    @Autowired // springboot가 미리 생성해놓은 객체를 가져다가 자동연결!
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";

    }

    // FormPage에서 submmit시 createArticle method이 바로 호출된다. 이때 forampage값들이 param으로 넘어간다.
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString());
        //1. DTO -> Entity
        Article article = form.toEntity();
        log.info(article.toString());

        //2. Repository에 Enttity를 DB 안에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        return "";
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id=: ",id);
        // 1: id를 데이터로 가져옴
        // Optional<Article> articleentity = articleRepository.findAllById(id);
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2: 가져온 데이터를 모델에 등록 veiw 페이지 사용을 위해
        model.addAttribute("article",articleEntity);

        // 3: 보여줄 Page를 설정
        return "articles/show";
    }
}
