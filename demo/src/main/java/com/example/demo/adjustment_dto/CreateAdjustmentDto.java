package com.example.demo.adjustment_dto;

import com.example.demo.adjustment.AdjustmentStatus;

import java.util.Calendar;

public class CreateAdjustmentDto {
    public AdjustmentStatus adjustmentStatus;
    public Calendar approvedDate;
    public String rejectedReason;
}
