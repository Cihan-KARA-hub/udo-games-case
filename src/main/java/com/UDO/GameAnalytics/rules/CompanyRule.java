package com.UDO.GameAnalytics.rules;

import com.UDO.GameAnalytics.core.exception.type.BusinessException;
import com.UDO.GameAnalytics.entity.Company;
import com.UDO.GameAnalytics.repository.CompanyRepository;
import com.UDO.GameAnalytics.service.CompanyServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CompanyRule {
    private final CompanyRepository companyRepository;

    public CompanyRule(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void findExistById(String name) {
        Optional<Company> company = companyRepository.findByName(name);
        if (company.isPresent()) {
            throw new BusinessException("Company with name " + name + " already exists");
        }
    }

}
