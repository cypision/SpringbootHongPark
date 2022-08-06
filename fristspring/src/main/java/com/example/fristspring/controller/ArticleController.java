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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

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
        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id=: ",id);
        // 1: id를 데이터로 가져옴
        // Optional<Article> articleentity = articleRepository.findAllById(id);
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2: 가져온 데이터를 모델에 등록 veiw 페이지 사용을 위해
        model.addAttribute("article",articleEntity);

        // 3: 보여줄 Page를 설정 = Redirect is important
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        //1) 모든 row를 가져온다.
        List<Article> articleEntitiyList = (List<Article>)articleRepository.findAll();

        //2) 가져온 Article 묶음을 뷰로 전달
        model.addAttribute("articleList",articleEntitiyList);

        //3) return page setting
        return "articles/index"; // view page를 지정해준다. 해당 자원은 templetes 의 mustache 파일.
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){ //Getmappgin과 동시에 edit function실행되며, param을 가져온다. model은 실행시 자동생성되는듯...
        //수정할 Data가져오기 DB에서 가져오니깐. 받는 객체는 Entity객체 Article articleEntity 이다.
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // add model object attribute
        model.addAttribute("article",articleEntity);
        return "articles/edit"; // show edit page
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());
        //1) DTO to Article Entity
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        //2) Update Entity with updateValue(=DTO)
        // 2-1: DB에서 기존 데이터를 가져옴
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2-2: 기존 데이터가 있다면, 값을 갱신
        if (target != null) {
            articleRepository.save(articleEntity);
        }

        //3) redirect 수정결과Page
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다!!");
        // 1: 삭제 대상을 가져옴
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        // 2: 대상을 삭제
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }
        // 3: 결과 페이지로 리다이렉트
        return "redirect:/articles";
    }
}
