package com.shopping.tracking.service;

import com.shopping.tracking.model.ActionLog;
import com.shopping.tracking.model.OnePattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrackingApiLogicServiceTest {

    @Autowired
    TrackingApiLogicService trackingApiLogicService;

    @Test
    void getTokenizedLogsOfUser() {
        List<OnePattern> patternList = trackingApiLogicService.getTokenizedLogsOfUser("shopad");

        for(OnePattern onePattern : patternList) {
            System.out.println(onePattern);
        }

        assertNotNull(patternList);
    }
}