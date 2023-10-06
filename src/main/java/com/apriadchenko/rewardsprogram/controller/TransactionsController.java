package com.apriadchenko.rewardsprogram.controller;

import com.apriadchenko.rewardsprogram.dto.TransactionDto;
import com.apriadchenko.rewardsprogram.dto.response.BaseResponse;
import com.apriadchenko.rewardsprogram.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionsController extends BaseController{
    private final TransactionsService transactionsService;

    @PostMapping(value = "transactions/add")
    public ResponseEntity<BaseResponse> addTransaction(@RequestBody TransactionDto transactionDto) {
        transactionsService.addTransaction(transactionDto);
        return ResponseEntity.ok(sendResponse(new BaseResponse()));
    }

    @PatchMapping(value = "transactions/update/{id}")
    public ResponseEntity<BaseResponse> updateTransaction(@PathVariable Integer id, @RequestBody TransactionDto transactionDto) {
        transactionsService.updateTransaction(id, transactionDto);
        return ResponseEntity.ok(sendResponse(new BaseResponse()));
    }
}
