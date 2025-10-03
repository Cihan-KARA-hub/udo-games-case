package com.UDO.GameAnalytics.rules;

import com.UDO.GameAnalytics.entity.Company;
import com.UDO.GameAnalytics.entity.Game;
import com.UDO.GameAnalytics.repository.GameRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameRule {
    private final GameRepository gameRepository;

    public GameRule(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
}
