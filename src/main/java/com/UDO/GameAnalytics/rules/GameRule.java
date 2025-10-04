package com.UDO.GameAnalytics.rules;

import com.UDO.GameAnalytics.entity.Company;
import com.UDO.GameAnalytics.entity.Game;
import com.UDO.GameAnalytics.repository.GameRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GameRule {
    private final GameRepository gameRepository;

    public GameRule(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void findExistByGameName(String gameName) {
        Optional<Game> game = gameRepository.findByName(gameName);
        if (game.isPresent()) {
            throw new RuntimeException("Game with name " + gameName + " already exists");
        }
    }
    public Game findExistById(Long gameId) {
       return gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game with id " + gameId + " does not exist"));
    }
}
