package com.eddie.springboot.web;

import com.eddie.springboot.service.posts.PostsService;
import com.eddie.springboot.web.dto.PostsListResponseDto;
import com.eddie.springboot.web.dto.PostsResponseDto;
import com.eddie.springboot.web.dto.PostsSaveRequestDto;
import com.eddie.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    // 아래 findAllDesc(), findById(), delete() 에 대한 테스트코드 필요
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id){
        return postsService.findById(id);
    }

    // 해당 API, 책에서는 REST API 가 아니라 템플릿 엔진으로 결과값을 전달하는 방식으로 구현했지만
    // 내 코드에서는 REST API 로 변경
    @GetMapping("/api/v1/posts")
    public List<PostsListResponseDto> findAllDesc() {
        return postsService.findAllDesc();
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}