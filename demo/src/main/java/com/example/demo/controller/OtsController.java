package com.example.demo.controller;

import com.example.demo.dto.ots.CreateOtsDto;
import com.example.demo.dto.ots.UpdateOtsDto;
import com.example.demo.model.ots.Ots;
import com.example.demo.service.ots.OtsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class OtsController {
    @Autowired
    private OtsService otsService;

    @GetMapping(value = "/{id}")
    public Ots getOts(@PathVariable Long id){
        return otsService.getOts(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ots createPayment(@RequestBody CreateOtsDto source){
        return otsService.createOts(source);
    }

    @PutMapping
    public void putOts(@RequestBody UpdateOtsDto source){
        otsService.updateOts(source);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOts(@PathVariable Long id){
        otsService.deleteOts(id);
    }
}
