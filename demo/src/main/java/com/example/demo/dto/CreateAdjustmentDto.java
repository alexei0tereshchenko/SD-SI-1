package com.example.demo.dto;

import com.example.demo.model.AdjustmentStatus;

import java.util.Calendar;

public class CreateAdjustmentDto {
    public AdjustmentStatus adjustmentStatus;
    public Calendar approvedDate;
    public String rejectedReason;
}
