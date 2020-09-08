package com.shopping.tracking.ifs;

import com.shopping.tracking.model.ActionLog;
import com.shopping.tracking.model.Header;
import com.shopping.tracking.model.OnePattern;

import java.util.List;

public interface LogInterface<Req, Res> {
    Header<Res> saveLog(Header<Req> log);
    List<ActionLog> getAllLogsOfUser(String userId);
    List<OnePattern> getTokenizedLogsOfUser(String userId);
}
