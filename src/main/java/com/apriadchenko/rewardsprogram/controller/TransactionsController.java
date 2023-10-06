package com.apriadchenko.rewardsprogram.controller;

import com.apriadchenko.rewardsprogram.dto.AddTransactionDto;
import com.apriadchenko.rewardsprogram.dto.response.BaseResponse;
import com.apriadchenko.rewardsprogram.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionsController extends BaseController{
    private final TransactionsService transactionsService;

    @PostMapping(value = "transactions/add")
    public ResponseEntity<BaseResponse> addTransaction(@RequestBody AddTransactionDto addTransactionDto) {
        transactionsService.addTransaction(addTransactionDto);
        return ResponseEntity.ok(sendResponse(new BaseResponse()));
    }
}
