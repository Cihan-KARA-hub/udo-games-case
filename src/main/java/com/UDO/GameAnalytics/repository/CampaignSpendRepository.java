package com.UDO.GameAnalytics.repository;

import com.UDO.GameAnalytics.entity.CampaignSpend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignSpendRepository extends JpaRepository<CampaignSpend, Long> {
}
