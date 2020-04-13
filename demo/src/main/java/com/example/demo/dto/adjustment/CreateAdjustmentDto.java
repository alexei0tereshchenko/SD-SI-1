package com.example.demo.dto.adjustment;

import com.example.demo.model.adjustment.AdjustmentStatus;

import java.util.Calendar;

public class CreateAdjustmentDto {
    public AdjustmentStatus adjustmentStatus;
    public Calendar approvedDate;
    public String rejectedReason;
}
