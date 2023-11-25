package com.blinder.api.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReportRequestDto {
    private String reason;
    private String description;
    private String reporterId;
    private String reportedId;
}
