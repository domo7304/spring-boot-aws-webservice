package com.eddie.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    // Spring Data JPA 를 통해서도 아래와 같은 쿼리를 해결할 수 있지만
    // @Query 와 Spring Data JPA 의 가독성을 비교해보고 선택해서 사용하면 된다.
    @Query("select p from Posts p order by p.id desc ")
    List<Posts> findAllDesc();
}
