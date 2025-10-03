package com.UDO.GameAnalytics.mapper;

import com.UDO.GameAnalytics.dto.company.request.CreateCompanyRequestDto;
import com.UDO.GameAnalytics.dto.company.response.CreateCompanyResponseDto;
import com.UDO.GameAnalytics.entity.Company;

public class CompanyMapper {

    public static Company createCompanyRequestDtoToEntity(CreateCompanyRequestDto createGameRequestDto,Integer nameCounter) {
        Company company = new Company();
        company.setName(createGameRequestDto.getName()+" "+nameCounter);
        return company;
    }
    public static CreateCompanyResponseDto entityToCreateCompanyResponseDto(Company company) {
     return new CreateCompanyResponseDto(company.getName(), "Company created");
    }
}
