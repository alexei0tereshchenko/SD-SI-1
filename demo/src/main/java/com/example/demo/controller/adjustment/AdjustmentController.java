package com.example.demo.controller.adjustment;

import com.example.demo.dto.adjustment.CreateAdjustmentDto;
import com.example.demo.dto.adjustment.UpdateAdjustmentDto;
import com.example.demo.model.adjustment.Adjustment;
import com.example.demo.service.adjustment.AdjustmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdjustmentController {
    @Autowired
    private AdjustmentService adjustmentService;

    @RequestMapping("/adjustment/create")
    public Adjustment createAdjustment(@RequestBody CreateAdjustmentDto source) throws Exception {
        try {
            return adjustmentService.createAdjustment(source);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    @PutMapping(value = "/{id}")
    public void updateAdjustment(@RequestBody UpdateAdjustmentDto source, @PathVariable Long id) throws Exception {
        try {
            adjustmentService.updateAdjustment(source,id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    @PutMapping(value = "/adjustment/cancel/{id}")
    public void cancelAdjustment(@PathVariable Long id) throws Exception {
        try {
            adjustmentService.cancelAdjustment(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    @GetMapping(value = "/{id}")
    public Adjustment getAdjustment(@PathVariable Long id) throws Exception {
        try {
           return adjustmentService.getAdjustment(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    @DeleteMapping(value = "/{id}")
    public void deleteAdjustment(@PathVariable Long id) throws Exception {
        try {
            adjustmentService.deleteAdjustment(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


}
