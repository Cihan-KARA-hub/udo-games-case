package com.UDO.GameAnalytics.repository;

import com.UDO.GameAnalytics.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByGameId(Long gameId);
}
