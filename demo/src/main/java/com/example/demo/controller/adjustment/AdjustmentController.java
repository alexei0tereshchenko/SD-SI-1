package com.example.demo.controller.adjustment;

import com.example.demo.dto.adjustment.CreateAdjustmentDto;
import com.example.demo.model.adjustment.Adjustment;
import com.example.demo.service.adjustment.AdjustmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdjustmentController {
    @Autowired
    private AdjustmentService adjustmentService;

    @RequestMapping("/adjustment/create")
    public Adjustment createAdjustment(@RequestBody CreateAdjustmentDto source){
        return adjustmentService.createAdjustment(source);
    }

}
