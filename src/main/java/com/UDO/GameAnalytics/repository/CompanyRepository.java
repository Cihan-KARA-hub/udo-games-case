package com.UDO.GameAnalytics.repository;

import com.UDO.GameAnalytics.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}
