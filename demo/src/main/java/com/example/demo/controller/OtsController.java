package com.example.demo.controller;

import com.example.demo.dto.ots.CreateOtsDto;
import com.example.demo.dto.ots.UpdateOtsDto;
import com.example.demo.model.ots.Ots;
import com.example.demo.service.ots.OtsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ots")
@CrossOrigin
public class OtsController {
    @Autowired
    private OtsService otsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ots createOts(@RequestBody CreateOtsDto source) throws OtsController.OtsControllerException {
        try{ return otsService.createOts(source); }
        catch (OtsService.OtsServiceException e){ throw new OtsController.OtsControllerException(e); }
    }

    @PutMapping(value = "/{id}")
    public Ots putOts(@RequestBody UpdateOtsDto source, @PathVariable Long id) throws OtsController.OtsControllerException {
        try{ return otsService.updateOts(source, id); }
        catch (OtsService.OtsServiceException e){ throw new OtsController.OtsControllerException(e); }
    }

    @PutMapping(value = "/terminate/{id}")
    public Ots terminateOts(@PathVariable Long id) throws OtsController.OtsControllerException {
        try{ return otsService.terminateOts(id); }
        catch (OtsService.OtsServiceException e){ throw new OtsController.OtsControllerException(e); }
    }

    @GetMapping(value = "/{id}")
    public Ots getPayment(@PathVariable Long id) throws OtsController.OtsControllerException {
        try{ return otsService.getOts(id); }
        catch (OtsService.OtsServiceException e){ throw new OtsController.OtsControllerException(e); }
    }

    @GetMapping(value = "/account/{accountId}")
    public List<Ots> getBillingAccountOts(@PathVariable Long accountId) throws OtsController.OtsControllerException {
        try{ return otsService.getBillingAccountOts(accountId); }
        catch (OtsService.OtsServiceException e){ throw new OtsController.OtsControllerException(e); }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOts(@PathVariable Long id) throws OtsController.OtsControllerException {
        try{ otsService.deleteOts(id); }
        catch (OtsService.OtsServiceException e){ throw new OtsController.OtsControllerException(e); }
    }

    public static class OtsControllerException extends Exception {
        public OtsControllerException(Exception cause) {
            super(cause.getMessage(), cause);
        }
    }
}
