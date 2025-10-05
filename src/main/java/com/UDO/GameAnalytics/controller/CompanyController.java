package com.UDO.GameAnalytics.controller;

import com.UDO.GameAnalytics.dto.company.request.CreateCompanyRequestDto;
import com.UDO.GameAnalytics.dto.company.response.CompanyProfitResponseDto;
import com.UDO.GameAnalytics.dto.company.response.CreateCompanyResponseDto;
import com.UDO.GameAnalytics.dto.event.request.CreateEventRequestDto;
import com.UDO.GameAnalytics.dto.event.response.CreateEventResponseDto;
import com.UDO.GameAnalytics.entity.Company;
import com.UDO.GameAnalytics.service.CompanyServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/games/")
public class CompanyController {
    private final CompanyServiceImpl companyService;

    public CompanyController(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }

    @PostMapping("company")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCompanyResponseDto createEvent(@RequestBody @Valid CreateCompanyRequestDto requestDto) {
        return companyService.createCompany(requestDto);
    }
   @GetMapping("{companyId}/company-profit")
   @ResponseStatus(HttpStatus.OK)
    public CompanyProfitResponseDto profit(@PathVariable Long companyId) {
        return companyService.profit(companyId);
    }
}
