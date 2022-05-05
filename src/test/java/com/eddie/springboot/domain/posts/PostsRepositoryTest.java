package com.eddie.springboot.domain.posts;

import com.eddie.springboot.domain.posts.Posts;
import com.eddie.springboot.domain.posts.PostsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// @SpringBootTest 가 이미 @ExtendWith(SpringExtension.class) 를 갖고 있으므로
// @SpringBootTest 를 사용한다면 @ExtendWith(SpringExtension.class) 를 적어줄 필요가 없다.
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("domo7304@naver.com")
                .build());

        // when
        List<Posts> postsLis = postsRepository.findAll();

        // then
        Posts posts = postsLis.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
