package com.UDO.GameAnalytics.dto.company.response;

public class CreateCompanyResponseDto {
    private String companyName;
    private String message;

    public CreateCompanyResponseDto(String companyName, String message) {
        this.companyName = companyName;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}
