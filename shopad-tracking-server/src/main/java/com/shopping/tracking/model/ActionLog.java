package com.shopping.tracking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tracking")
public class ActionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성, mysql에서는 IDENTITY로 설정
    private Long id;
    private String userId;           // 일단은 한 명 사용자만 있어서, userId 는 "shopad"
    private String action;           // categoryClick, contentClick, scroll 중 하나가 들어감
    private String actionDetail;     // categoryId, productId, scroll 횟수 중 하나가 들어감
    private LocalDateTime timestamp; // 액션 발생 시간
    private Boolean startPoint;      // 동선 패턴의 시작점 여부
}
