package com.example.demo.service.adjustment;

import com.example.demo.dto.adjustment.CreateAdjustmentDto;
import com.example.demo.integration.adjustment.AdjustmentIntegrationService;
import com.example.demo.model.adjustment.Adjustment;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdjustmentService {
    @Autowired
    AdjustmentIntegrationService adjustmentIntegrationService;

    public Adjustment createAdjustment(CreateAdjustmentDto source){
        Adjustment adjustment = new Adjustment();
        adjustment.setRejectedReason(source.rejectedReason);
        adjustment.setAdjustmentStatus(source.adjustmentStatus);
        adjustment.setApprovedDate(source.approvedDate);
        adjustmentIntegrationService.createAdjustment(adjustment);
        return adjustment;
    }


}
