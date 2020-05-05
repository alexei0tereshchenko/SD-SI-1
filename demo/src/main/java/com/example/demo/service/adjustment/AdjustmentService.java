package com.example.demo.service.adjustment;

import com.example.demo.dto.adjustment.CreateAdjustmentDto;
import com.example.demo.dto.adjustment.UpdateAdjustmentDto;
import com.example.demo.integration.adjustment.AdjustmentIntegrationService;
import com.example.demo.model.adjustment.Adjustment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdjustmentService {
    @Autowired
    AdjustmentIntegrationService adjustmentIntegrationService;

    public Adjustment createAdjustment(CreateAdjustmentDto source) throws Exception {
        Adjustment adjustment = new Adjustment();
        adjustment.setRejectedReason(source.rejectedReason);
        adjustment.setAdjustmentStatus(source.adjustmentStatus);
        adjustment.setApprovedDate(source.approvedDate);
        adjustmentIntegrationService.createAdjustment(adjustment);
        return adjustment;
    }
    public void updateAdjustment(UpdateAdjustmentDto source, Long id) throws Exception {
        try {
            adjustmentIntegrationService.updateAdjustment(id,source);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    public Adjustment getAdjustment(Long id) throws Exception {
        try {
            return adjustmentIntegrationService.getAdjustment(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    public void deleteAdjustment(Long id) throws Exception {
        try {
            adjustmentIntegrationService.deleteAdjustment(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    public void cancelAdjustment(Long id) throws Exception {
        try {
            adjustmentIntegrationService.cancelAdjustment(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


}
