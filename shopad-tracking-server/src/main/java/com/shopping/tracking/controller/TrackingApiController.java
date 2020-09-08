package com.shopping.tracking.controller;

import com.shopping.tracking.model.*;
import com.shopping.tracking.repository.TrackingRepository;
import com.shopping.tracking.service.TrackingApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/trackingUser")
public class TrackingApiController {

    @Autowired
    private TrackingApiLogicService trackingApiLogicService;

    @PostMapping("/api") // trackingUser/api
    @CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
    public Header<TrackingApiResponse> saveLog(@RequestBody Header<TrackingApiRequest> reqLog) {

        log.info("reqLog: ", reqLog);
        return trackingApiLogicService.saveLog(reqLog);
    }

    @GetMapping("/api/{userId}") // trackingUser/api/{userId}
    public List<OnePattern> getTokenizedLogsOfUser(@PathVariable(name="userId") String userId) {

        log.info("get All Tokenized Logs of {}", userId);
        return trackingApiLogicService.getTokenizedLogsOfUser(userId);
    }


}
