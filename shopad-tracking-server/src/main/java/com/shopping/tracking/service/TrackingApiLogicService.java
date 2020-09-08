package com.shopping.tracking.service;

import com.shopping.tracking.ifs.LogInterface;
import com.shopping.tracking.model.*;
import com.shopping.tracking.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrackingApiLogicService implements LogInterface<TrackingApiRequest, TrackingApiResponse>{

    @Autowired
    private TrackingRepository trackingRepository;

    @Override
    public Header<TrackingApiResponse> saveLog(Header<TrackingApiRequest> req) {

        TrackingApiRequest trackingApiRequest = req.getData();

        ActionLog actionLog = ActionLog.builder()
                .userId(trackingApiRequest.getUserId())
                .action(trackingApiRequest.getAction())
                .actionDetail(trackingApiRequest.getActionDetail())
                .timestamp(trackingApiRequest.getTimestamp())
                .startPoint(trackingApiRequest.getStartPoint())
                .build();

        return response(trackingRepository.save(actionLog));
    }

    @Override
    public List<ActionLog> getAllLogsOfUser(String userId) {
        return trackingRepository.findAllByUserId(userId);
    }

    @Override
    public List<OnePattern> getTokenizedLogsOfUser(String userId) {

        List<ActionLog> allLogsOfUser = trackingRepository.findAllByUserId(userId);

        List<OnePattern> tokenizedLogsOfUser = new ArrayList<>();
        List<ActionLog> logsInOnePattern = new ArrayList<>();

        for(ActionLog actionLog : allLogsOfUser) {

            if(actionLog.getStartPoint()){ // 한 패턴의 시작점인 로그일 때
                OnePattern onePattern = new OnePattern();
                onePattern.setLogsInPattern(logsInOnePattern);

                tokenizedLogsOfUser.add(onePattern);
                logsInOnePattern = new ArrayList<>();
            }
            
            logsInOnePattern.add(actionLog);
        }

        return tokenizedLogsOfUser;
    }

    private Header<TrackingApiResponse> response(ActionLog actionLog) {
        TrackingApiResponse userApiResponse = TrackingApiResponse.builder()
                .id(actionLog.getId())
                .userId(actionLog.getUserId())
                .action(actionLog.getAction())
                .actionDetail(actionLog.getActionDetail())
                .timestamp(actionLog.getTimestamp())
                .startPoint(actionLog.getStartPoint())
                .build();

        return Header.OK(userApiResponse);
    }
}
