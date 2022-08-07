package com.example.fristspring.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //일반 Controller는 view page를 반환하고, RestController는 데이터만을 반환하기 위한것.
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello(){
        return "RestApiController Hello!";

    }
}
