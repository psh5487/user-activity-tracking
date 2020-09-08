package com.shopping.tracking.controller;

import com.shopping.tracking.model.ActionLog;
import com.shopping.tracking.repository.TrackingRepository;
import com.shopping.tracking.service.TrackingApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class TrackingController {

    @Autowired
    private TrackingApiLogicService trackingApiLogicService;

    @GetMapping("/tracking")
    public String hello() {
        System.out.println("hello!");
        return "index";
    }

    @GetMapping("/makingFile")
    public void makingFile(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // db에서 모든 트래킹 가져옴
        List<ActionLog> allActionLog = trackingApiLogicService.getAllLogsOfUser("shopad");

        // File 생성하기
        FileWriter f = new FileWriter("/Users/sohyun/Desktop/tracking.csv", true);
        BufferedWriter fw = new BufferedWriter(f);
        fw.write("id,user_id,action,number_about_action,timestamp,duration");
        fw.newLine();

        int resultCount = 1;
        for(ActionLog obj : allActionLog) {
            fw.write(obj.getId()+","+obj.getUserId()+","
                    +obj.getAction()+","+obj.getActionDetail()+","
                    +obj.getTimestamp()
            );
            fw.newLine();
        }

        System.out.println("csv file created!");

        fw.flush();
        fw.close();

        //컨텍스트 페스 경로가져오기
        String context = request.getContextPath();

        //알림창
        PrintWriter out = response.getWriter();
        out.println("<script>alert('csv File Created!'); location.href='"+context+"/tracking';</script>");
        out.flush();
    }

}
