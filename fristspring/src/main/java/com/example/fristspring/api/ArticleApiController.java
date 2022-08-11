package com.example.fristspring.api;

import com.example.fristspring.ArticleService.ArticleService;
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

    @Autowired
    private ArticleService articleService;

    @Autowired // Dependency Injection
    private ArticleRepository articleRepository;

    //GET list
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleService.index();
    }

    //GET onlye one row
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

    // POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        Article created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                                          @RequestBody ArticleForm dto) {
        Article updated = articleService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
//    public ResponseEntity<Article> update(@PathVariable Long id,
//                                          @RequestBody ArticleForm dto) {
//        // 1: DTO -> 엔티티
//        Article article = dto.toEntity();
//        log.info("id: {}, article: {}", id, article.toString());
//        // 2: 타겟 조회
//        Article target = articleRepository.findById(id).orElse(null);
//        // 3: 잘못된 요청 처리
//        if (target == null || id != article.getId()) {
//            // 400, 잘못된 요청 응답!
//            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // body에는 아무것도 안싣는다.
//        }
//        // 4: 업데이트 및 정상 응답(200)
//        target.patch(article);  // add Fucntion where Article class = entity class. Post요청에서 안보내값은 NULL처리않고 원래값을 그대로 두는 용도
//        Article updated = articleRepository.save(target); // target.patch과정에서, 새로 받아온 update값들이 반영되었고, 그걸 저장한다.
//        return ResponseEntity.status(HttpStatus.OK).body(updated);
//    }

    // Delete
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // 트랜잭션 -> 실패 -> 롤백!
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) {
        List<Article> createdList = articleService.createArticles(dtos);
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
