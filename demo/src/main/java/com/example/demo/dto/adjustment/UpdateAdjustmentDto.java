package com.example.demo.dto.adjustment;

import com.example.demo.model.adjustment.AdjustmentStatus;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateAdjustmentDto {
    public String name;
    public long parentId;
    public AdjustmentStatus adjustmentStatus;
    public Date approvedDate;
    public String rejectedReason;
}