package com.shopping.tracking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackingApiResponse {
    private Long id;
    private String userId;
    private String action;
    private String actionDetail;
    private LocalDateTime timestamp;
    private Boolean startPoint;
}
