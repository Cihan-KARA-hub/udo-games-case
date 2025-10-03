package com.UDO.GameAnalytics.repository;

import com.UDO.GameAnalytics.entity.Company;
import com.UDO.GameAnalytics.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {
    List<Game> findByName(String name);
}
