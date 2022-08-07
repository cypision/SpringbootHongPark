package com.example.fristspring.api;

import com.example.fristspring.dto.ArticleForm;
import com.example.fristspring.entity.Article;
import com.example.fristspring.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //RestAPI 용 콘트럴러. data JSON을 반환한다.
@Slf4j // logging
public class ArticleApiController {

    @Autowired // Dependency Injection
    private ArticleRepository articleRepository;

    //GET list
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }

    //GET onlye one row
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }

    // POST
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto) { //RequestBody api json으로 들어올때는 어디서 데이터 받아올지 명시되어야 함
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                                          @RequestBody ArticleForm dto) {
        // 1: DTO -> 엔티티
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());
        // 2: 타겟 조회
        Article target = articleRepository.findById(id).orElse(null);
        // 3: 잘못된 요청 처리
        if (target == null || id != article.getId()) {
            // 400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // body에는 아무것도 안싣는다.
        }
        // 4: 업데이트 및 정상 응답(200)
        target.patch(article);  // add Fucntion where Article class = entity class. Post요청에서 안보내값은 NULL처리않고 원래값을 그대로 두는 용도
        Article updated = articleRepository.save(target); // target.patch과정에서, 새로 받아온 update값들이 반영되었고, 그걸 저장한다.
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // Delete
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        // 잘못된 요청 처리
        if (target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 대상 삭제
        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
