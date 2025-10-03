package com.UDO.GameAnalytics.dto.game.response;

public class CreateGameResponseDto {
    private String name;
    private String message;

    public CreateGameResponseDto(String name, String message) {
        this.name = name;
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
