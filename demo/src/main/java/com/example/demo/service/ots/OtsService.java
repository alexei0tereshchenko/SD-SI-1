package com.example.demo.service.ots;

import com.example.demo.model.ots.Ots;
import com.example.demo.model.ots.OtsStatus;
import com.example.demo.integration.ots.OtsIntegrationService;
import com.example.demo.dto.ots.CreateOtsDto;
import com.example.demo.dto.ots.UpdateOtsDto;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtsService {
    @Autowired
    OtsIntegrationService otsIntegrationServiceOb;

    public Ots createOts(CreateOtsDto source){
        Ots ots = new Ots();

        ots.setParentId(source.getParentId());
        ots.setCancellationDate(new Date());
        ots.setOtsStatus(source.getOtsStatus());
        ots.setInstalments(false);
        ots.setCreatedWhen(new Date());
        ots.setCancellationDate(new Date());
        ots.setTerminationReason("Reason here");
        ots.setDescription("Description here");

        otsIntegrationServiceOb.createOts(ots);
        return ots;
    }

    public Ots getOts(Long id){
        return otsIntegrationServiceOb.getOts(id);
    }

    public void updateOts(UpdateOtsDto source){
        otsIntegrationServiceOb.updateOts(source);
    }

    public void deleteOts(Long id){
        otsIntegrationServiceOb.deleteOts(id);
    }
}
