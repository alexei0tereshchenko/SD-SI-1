package com.example.demo.dto.ots;

import com.example.demo.model.ots.OtsStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UpdateOtsDto {
    private String description;
    private Date createdWhen;
    private BigDecimal amount;
    private Long parentId;
    private OtsStatus otsStatus;
    private String terminationReason;
    private Boolean instalments;
    private Date cancellationDate;
}
