package com.shopping.tracking.model;

import lombok.Data;

import java.util.List;

@Data
public class OnePattern {
    private List<ActionLog> logsInPattern;
}
