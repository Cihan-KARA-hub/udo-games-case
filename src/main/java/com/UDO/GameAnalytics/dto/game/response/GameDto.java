package com.UDO.GameAnalytics.dto.game.response;

import com.UDO.GameAnalytics.entity.Game;

public class GameDto {
    private Long id;
    private String name;
    private Long companyId;
    private String companyName;

    public GameDto(Game game) {
        this.id = game.getId();
        this.name = game.getName();
        this.companyId = game.getCompany().getId();
        this.companyName = game.getCompany().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}