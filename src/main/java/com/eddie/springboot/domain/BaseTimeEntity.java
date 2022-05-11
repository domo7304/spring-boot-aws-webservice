package com.eddie.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
// JPA Entity 클래스들이 BaseTimeEntity 를 상속할 경우,
// createdData, modifiedDate 필드도 column 으로 인식하도록 해주는 어노테이션
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // baseTimeEntity 클래스에 Auditing 기능 포함
public class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
