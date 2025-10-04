package com.UDO.GameAnalytics.controller;

import com.UDO.GameAnalytics.dto.campaign.request.CreateCampaignRequestDto;
import com.UDO.GameAnalytics.dto.campaign.response.CampaignSpendResponseDto;
import com.UDO.GameAnalytics.dto.campaign.response.CreateCampaignResponseDto;
import com.UDO.GameAnalytics.entity.CampaignSpend;
import com.UDO.GameAnalytics.service.CampaignServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games/")
public class CampaignController {
    private final CampaignServiceImpl  campaignService;
    public CampaignController(CampaignServiceImpl campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping("{gameId}/campaign")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCampaignResponseDto create(@RequestBody @Valid CreateCampaignRequestDto campaignSpend,@PathVariable Long gameId) {
       return campaignService.create(campaignSpend,gameId);
    }
    ///api/v1/games/{game-id}/campaign-spent   bir oyun için toplam harcanan kapmanya tutar
    @GetMapping("{gameId}/campaign-spent")
    @ResponseStatus(HttpStatus.OK)
    public CampaignSpendResponseDto campaignSpentGameId(@PathVariable Long gameId){
        return  campaignService.getSpendGameId(gameId);
    }

}
