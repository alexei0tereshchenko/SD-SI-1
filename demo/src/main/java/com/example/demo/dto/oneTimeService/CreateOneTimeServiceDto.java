package com.example.demo.dto.oneTimeService;

import com.example.demo.model.oneTimeService.OneTimeServiceStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOneTimeServiceDto {
    private BigDecimal amount;
    private OneTimeServiceStatus otsStatus;
    private String terminationReason;
    private boolean instalments;
}
