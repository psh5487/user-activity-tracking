package com.shopping.tracking.repository;

import com.shopping.tracking.model.ActionLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrackingRepositoryTest {
    @Autowired
    private TrackingRepository trackingRepository;

    @Test
    @Transactional
    void findFirstByIdOrderByIdDesc() {
        ActionLog actionLog = trackingRepository.findFirstByIdOrderByIdDesc(1L);

        if(actionLog != null){
            System.out.println(actionLog);
        }

        assertNotNull(actionLog);
    }

    @Test
    @Transactional
    void findAllByUserId() {
        List<ActionLog> allByUserId = trackingRepository.findAllByUserId("shopad");

        for(ActionLog log : allByUserId) {
            System.out.println(log);
        }

        assertNotNull(allByUserId);
    }
}