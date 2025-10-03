package com.UDO.GameAnalytics.repository;

import com.UDO.GameAnalytics.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game,Long> {
}
