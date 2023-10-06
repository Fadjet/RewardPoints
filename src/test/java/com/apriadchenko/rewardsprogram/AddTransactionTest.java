package com.apriadchenko.rewardsprogram;

import com.apriadchenko.rewardsprogram.dto.TransactionDto;
import com.apriadchenko.rewardsprogram.dto.response.BaseResponse;
import com.apriadchenko.rewardsprogram.dto.response.RewardPointsResponseDto;
import com.apriadchenko.rewardsprogram.enums.ExceptionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddTransactionTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testAddTransaction() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        TransactionDto dto = TransactionDto.builder().customerId(4).transactionAmount(120.1).date(LocalDate.now().minusMonths(2).format(formatter)).build();
        BaseResponse baseResponse = restTemplate.postForObject("http://localhost:" + port + "/transactions/add", dto, BaseResponse.class);
        assertNotNull(baseResponse);
        assertNotNull(baseResponse.getResult());
        assertTrue(baseResponse.getResult().getSuccess());
        dto = TransactionDto.builder().customerId(4).transactionAmount(82.1).date(LocalDate.now().minusMonths(1).format(formatter)).build();
        baseResponse = restTemplate.postForObject("http://localhost:" + port + "/transactions/add", dto, BaseResponse.class);
        assertNotNull(baseResponse);
        assertNotNull(baseResponse.getResult());
        assertTrue(baseResponse.getResult().getSuccess());
        dto = TransactionDto.builder().customerId(4).transactionAmount(211.3).date(LocalDate.now().format(formatter)).build();
        baseResponse = restTemplate.postForObject("http://localhost:" + port + "/transactions/add", dto, BaseResponse.class);
        assertNotNull(baseResponse);
        assertNotNull(baseResponse.getResult());
        assertTrue(baseResponse.getResult().getSuccess());

        RewardPointsResponseDto response = restTemplate.getForObject("http://localhost:" + port + "/reward-points/by-customer-id/4", RewardPointsResponseDto.class);
        assertNotNull(response);
        assertNotNull(response.getResult());
        assertTrue(response.getResult().getSuccess());
        assertEquals(200, response.getResult().getReturnCode());
        assertEquals(394, response.getTotalRewardPoints());
        assertEquals(90, response.getRewardPointsPerMonth().get(LocalDate.now().minusMonths(2).getMonth().toString()));
        assertEquals(32, response.getRewardPointsPerMonth().get(LocalDate.now().minusMonths(1).getMonth().toString()));
        assertEquals(272, response.getRewardPointsPerMonth().get(LocalDate.now().getMonth().toString()));
    }

    @Test
    void testAddTransaction_CustomerNotFound() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        TransactionDto dto = TransactionDto.builder().customerId(555).transactionAmount(120.1).date(LocalDate.now().minusMonths(2).format(formatter)).build();
        BaseResponse response = restTemplate.postForObject("http://localhost:" + port + "/transactions/add", dto, BaseResponse.class);

        assertNotNull(response);
        assertNotNull(response.getResult());
        assertFalse(response.getResult().getSuccess());
        assertEquals(ExceptionType.CUSTOMER_NOT_FOUND.getErrorCode(), response.getResult().getErrorCode());
        assertEquals(ExceptionType.CUSTOMER_NOT_FOUND.getErrorMessage(), response.getResult().getErrorMessage());
    }

    @Test
    void testAddTransaction_NegativeCustomerId() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        TransactionDto dto = TransactionDto.builder().customerId(-1).transactionAmount(120.1).date(LocalDate.now().minusMonths(2).format(formatter)).build();
        BaseResponse response = restTemplate.postForObject("http://localhost:" + port + "/transactions/add", dto, BaseResponse.class);

        assertNotNull(response);
        assertNotNull(response.getResult());
        assertFalse(response.getResult().getSuccess());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_CUSTOMER_ID.getErrorCode(), response.getResult().getErrorCode());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_CUSTOMER_ID.getErrorMessage(), response.getResult().getErrorMessage());
    }

    @Test
    void testAddTransaction_EmptyCustomerId() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        TransactionDto dto = TransactionDto.builder().customerId(null).transactionAmount(120.1).date(LocalDate.now().minusMonths(2).format(formatter)).build();
        BaseResponse response = restTemplate.postForObject("http://localhost:" + port + "/transactions/add", dto, BaseResponse.class);

        assertNotNull(response);
        assertNotNull(response.getResult());
        assertFalse(response.getResult().getSuccess());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_CUSTOMER_ID.getErrorCode(), response.getResult().getErrorCode());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_CUSTOMER_ID.getErrorMessage(), response.getResult().getErrorMessage());
    }

    @Test
    void testAddTransaction_NegativeAmount() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        TransactionDto dto = TransactionDto.builder().customerId(1).transactionAmount(-120.1).date(LocalDate.now().minusMonths(2).format(formatter)).build();
        BaseResponse response = restTemplate.postForObject("http://localhost:" + port + "/transactions/add", dto, BaseResponse.class);

        assertNotNull(response);
        assertNotNull(response.getResult());
        assertFalse(response.getResult().getSuccess());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_AMOUNT.getErrorCode(), response.getResult().getErrorCode());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_AMOUNT.getErrorMessage(), response.getResult().getErrorMessage());
    }

    @Test
    void testAddTransaction_EmptyAmount() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        TransactionDto dto = TransactionDto.builder().customerId(1).transactionAmount(null).date(LocalDate.now().minusMonths(2).format(formatter)).build();
        BaseResponse response = restTemplate.postForObject("http://localhost:" + port + "/transactions/add", dto, BaseResponse.class);

        assertNotNull(response);
        assertNotNull(response.getResult());
        assertFalse(response.getResult().getSuccess());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_AMOUNT.getErrorCode(), response.getResult().getErrorCode());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_AMOUNT.getErrorMessage(), response.getResult().getErrorMessage());
    }

    @Test
    void testAddTransaction_IncorrectDate() {
        TransactionDto dto = TransactionDto.builder().customerId(1).transactionAmount(120.1).date("2023.11.11").build();
        BaseResponse response = restTemplate.postForObject("http://localhost:" + port + "/transactions/add", dto, BaseResponse.class);

        assertNotNull(response);
        assertNotNull(response.getResult());
        assertFalse(response.getResult().getSuccess());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_DATE.getErrorCode(), response.getResult().getErrorCode());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_DATE.getErrorMessage(), response.getResult().getErrorMessage());
    }
}
