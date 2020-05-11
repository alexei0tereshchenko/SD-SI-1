package com.example.demo.dto.ots;

import com.example.demo.model.ots.OtsStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOtsDto {
    private Long parentId;
    private BigDecimal amount;
    private Boolean instalments;
}
