package com.example.demo.service.ots;

import com.example.demo.model.ots.Ots;
import com.example.demo.model.ots.OtsStatus;
import com.example.demo.integration.ots.OtsIntegrationService;
import com.example.demo.dto.ots.CreateOtsDto;
import com.example.demo.dto.ots.UpdateOtsDto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtsService {
    @Autowired
    OtsIntegrationService otsIntegrationServiceOb;

    public Ots createOts(CreateOtsDto source) throws OtsServiceException{
        try{
            Ots ots = new Ots();

            ots.setObjectTypeId(5L);
            ots.setParentId(source.getParentId());
            ots.setCancellationDate(new Date());
            ots.setOtsStatus(OtsStatus.CREATED);
            ots.setAmount(source.getAmount());
            ots.setInstalments(source.getInstalments());
            ots.setCreatedWhen(new Date());
            ots.setCancellationDate(new Date());
            ots.setTerminationReason("Reason here");
            ots.setDescription("Description here");
            ots.setName("ots-" + source.getAmount().toString() + "-" + (new SimpleDateFormat("yyyyMMddHHmmss").format(ots.getCreatedWhen())));

            otsIntegrationServiceOb.createOts(ots);
            return ots;
        }
        catch (Exception e){
            throw new OtsServiceException(e);
        }
    }

    public Ots getOts(Long id) throws OtsServiceException{
        try{ return otsIntegrationServiceOb.getOts(id); }
        catch (Exception e) {throw new OtsServiceException(e);}
    }

    public List<Ots> getBillingAccountOts(Long accountId) throws OtsServiceException{
        try{ return otsIntegrationServiceOb.getBillingAccountOts(accountId); }
        catch (Exception e) {throw new OtsServiceException(e);}
    }

    public Ots updateOts(UpdateOtsDto source, Long id) throws OtsServiceException{
        try{ return otsIntegrationServiceOb.updateOts(source, id); }
        catch (Exception e) {throw new OtsServiceException(e);}
    }

    public void deleteOts(Long id) throws OtsServiceException{
        try{ otsIntegrationServiceOb.deleteOts(id); }
        catch (Exception e) {throw new OtsServiceException(e);}
    }

    public Ots terminateOts(Long id) throws OtsServiceException{
        try{ return otsIntegrationServiceOb.terminateOts(id); }
        catch (Exception e) {throw new OtsServiceException(e);}
    }

    public static class OtsServiceException extends Exception {
        public OtsServiceException(Exception cause) {
            super(cause.getMessage(), cause);
        }
    }
}
