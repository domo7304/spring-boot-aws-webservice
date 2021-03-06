package com.eddie.springboot.service.posts;

import com.eddie.springboot.domain.posts.Posts;
import com.eddie.springboot.domain.posts.PostsRepository;
import com.eddie.springboot.web.dto.PostsListResponseDto;
import com.eddie.springboot.web.dto.PostsResponseDto;
import com.eddie.springboot.web.dto.PostsSaveRequestDto;
import com.eddie.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    // service layer 에 대한 테스트코드 x, 나중에 전부 작성 필요

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    // @Transactional(readOnly = true) 및 transacion 에 대해 블로그 정리 참고
    @Transactional(readOnly = true) // 책에는 해당 어노테이션을 사용하지 않았지만, CUD 기능이 아니라면 사용하는 것을 추천.
    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        postsRepository.delete(posts);
    }
}
