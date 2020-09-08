package com.shopping.tracking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingApiRequest {
    private String userId;           // 일단은 한 명 사용자만 있어서, userId 는 "shopad"
    private String action;           // categoryClick, contentClick, scroll 중 하나가 들어감
    private String actionDetail;     // categoryId, productId, scroll 횟수 중 하나가 들어감
    private LocalDateTime timestamp; // 액션 발생 시간
    private Boolean startPoint;      // 동선 패턴의 시작점 여부
}
