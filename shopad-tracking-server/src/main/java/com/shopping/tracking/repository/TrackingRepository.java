package com.shopping.tracking.repository;

import com.shopping.tracking.model.ActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingRepository extends JpaRepository<ActionLog, Long> {
    ActionLog findFirstByIdOrderByIdDesc(Long id);
    ActionLog findFirstByUserIdOrderByTimestamp(String userId);

    List<ActionLog> findAllByUserId(String userId);
}
