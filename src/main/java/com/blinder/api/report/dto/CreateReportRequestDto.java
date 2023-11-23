package com.blinder.api.report.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReportRequestDto {
    @Size(min = 1, max = 20)
    @NotBlank
    private String reason;
    @Size(max = 200)
    private String description;
    @NotBlank
    private String reportedId;
    @NotBlank
    private String reporterId;
}
