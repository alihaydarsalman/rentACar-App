package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.CardInfoService;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.dtos.get.GetCardInfoDto;
import com.turkcell.rentACar.entities.dtos.list.CardInfoListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cardInfos")
public class CardInfosController {

    private final CardInfoService cardInfoService;

    @Autowired
    public CardInfosController(CardInfoService cardInfoService) {
        this.cardInfoService = cardInfoService;
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int cardId) throws BusinessException{
        return this.cardInfoService.delete(cardId);
    }

    @GetMapping("/getAll")
    public DataResult<List<CardInfoListDto>> getAll() {
        return this.cardInfoService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<GetCardInfoDto> getById(@RequestParam int cardId) throws BusinessException{
        return this.cardInfoService.getById(cardId);
    }
}
