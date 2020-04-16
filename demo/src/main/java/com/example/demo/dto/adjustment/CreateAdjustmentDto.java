package com.example.demo.dto.adjustment;

import com.example.demo.model.adjustment.AdjustmentStatus;

import java.util.Date;

public class CreateAdjustmentDto {
    public AdjustmentStatus adjustmentStatus;
    public Date approvedDate;
    public String rejectedReason;
}
