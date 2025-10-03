package com.UDO.GameAnalytics.dto.company.request;

import jakarta.validation.constraints.NotBlank;


public class CreateCompanyRequestDto {
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
