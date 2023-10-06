package com.apriadchenko.rewardsprogram;

import com.apriadchenko.rewardsprogram.dto.TransactionDto;
import com.apriadchenko.rewardsprogram.dto.response.BaseResponse;
import com.apriadchenko.rewardsprogram.dto.response.RewardPointsResponseDto;
import com.apriadchenko.rewardsprogram.enums.ExceptionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpdateTransactionTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    void testUpdateTransaction() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        TransactionDto dto = TransactionDto.builder().customerId(3).transactionAmount(120.1).date(LocalDate.now().minusMonths(2).format(formatter)).build();
        BaseResponse baseResponse = restTemplate.postForObject("http://localhost:" + port + "/transactions/add", dto, BaseResponse.class);
        assertNotNull(baseResponse);
        assertNotNull(baseResponse.getResult());
        assertTrue(baseResponse.getResult().getSuccess());
        RewardPointsResponseDto user1Response = restTemplate.getForObject("http://localhost:" + port + "/reward-points/by-customer-id/3", RewardPointsResponseDto.class);
        assertNotNull(user1Response);
        assertNotNull(user1Response.getResult());
        assertTrue(user1Response.getResult().getSuccess());
        assertEquals(200, user1Response.getResult().getReturnCode());
        assertEquals(90, user1Response.getTotalRewardPoints());
        assertEquals(90, user1Response.getRewardPointsPerMonth().get(LocalDate.now().minusMonths(2).getMonth().toString()));

        dto = TransactionDto.builder().customerId(2).transactionAmount(122.1).date(LocalDate.now().minusMonths(2).format(formatter)).build();
        baseResponse = restTemplate.patchForObject("http://localhost:" + port + "/transactions/update/1", dto, BaseResponse.class);
        assertNotNull(baseResponse);
        assertNotNull(baseResponse.getResult());
        assertTrue(baseResponse.getResult().getSuccess());
        RewardPointsResponseDto user2Response = restTemplate.getForObject("http://localhost:" + port + "/reward-points/by-customer-id/2", RewardPointsResponseDto.class);
        assertNotNull(user2Response);
        assertNotNull(user2Response.getResult());
        assertTrue(user2Response.getResult().getSuccess());
        assertEquals(200, user2Response.getResult().getReturnCode());
        assertEquals(94, user2Response.getTotalRewardPoints());
        assertEquals(94, user2Response.getRewardPointsPerMonth().get(LocalDate.now().minusMonths(2).getMonth().toString()));

        user1Response = restTemplate.getForObject("http://localhost:" + port + "/reward-points/by-customer-id/1", RewardPointsResponseDto.class);
        assertNotNull(user1Response);
        assertNotNull(user1Response.getResult());
        assertTrue(user1Response.getResult().getSuccess());
        assertEquals(200, user1Response.getResult().getReturnCode());
        assertEquals(0, user1Response.getTotalRewardPoints());
        assertTrue(user1Response.getRewardPointsPerMonth().isEmpty());

    }

    @Test
    void testUpdateTransaction_CustomerNotFound() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        TransactionDto dto = TransactionDto.builder().customerId(1).transactionAmount(120.1).date(LocalDate.now().minusMonths(2).format(formatter)).build();
        BaseResponse response = restTemplate.postForObject("http://localhost:" + port + "/transactions/add", dto, BaseResponse.class);
        assertNotNull(response);
        assertNotNull(response.getResult());
        assertTrue(response.getResult().getSuccess());
        dto = TransactionDto.builder().customerId(5555).transactionAmount(120.1).date(LocalDate.now().minusMonths(2).format(formatter)).build();
        response = restTemplate.patchForObject("http://localhost:" + port + "/transactions/update/1", dto, BaseResponse.class);


        assertNotNull(response);
        assertNotNull(response.getResult());
        assertFalse(response.getResult().getSuccess());
        assertEquals(ExceptionType.CUSTOMER_NOT_FOUND.getErrorCode(), response.getResult().getErrorCode());
        assertEquals(ExceptionType.CUSTOMER_NOT_FOUND.getErrorMessage(), response.getResult().getErrorMessage());
    }

    @Test
    void testUpdateTransaction_TransactionNotFound() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        TransactionDto dto = TransactionDto.builder().customerId(1).transactionAmount(120.1).date(LocalDate.now().minusMonths(2).format(formatter)).build();
        BaseResponse response = restTemplate.postForObject("http://localhost:" + port + "/transactions/add", dto, BaseResponse.class);
        assertNotNull(response);
        assertNotNull(response.getResult());
        assertTrue(response.getResult().getSuccess());
        dto = TransactionDto.builder().customerId(5555).transactionAmount(120.1).date(LocalDate.now().minusMonths(2).format(formatter)).build();
        response = restTemplate.patchForObject("http://localhost:" + port + "/transactions/update/555", dto, BaseResponse.class);

        assertNotNull(response);
        assertNotNull(response.getResult());
        assertFalse(response.getResult().getSuccess());
        assertEquals(ExceptionType.TRANSACTION_NOT_FOUND.getErrorCode(), response.getResult().getErrorCode());
        assertEquals(ExceptionType.TRANSACTION_NOT_FOUND.getErrorMessage(), response.getResult().getErrorMessage());
    }

    @Test
    void testUpdateTransaction_NegativeCustomerId() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        TransactionDto dto = TransactionDto.builder().customerId(-1).transactionAmount(120.1).date(LocalDate.now().minusMonths(2).format(formatter)).build();
        BaseResponse response = restTemplate.patchForObject("http://localhost:" + port + "/transactions/update/1", dto, BaseResponse.class);

        assertNotNull(response);
        assertNotNull(response.getResult());
        assertFalse(response.getResult().getSuccess());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_CUSTOMER_ID.getErrorCode(), response.getResult().getErrorCode());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_CUSTOMER_ID.getErrorMessage(), response.getResult().getErrorMessage());
    }

    @Test
    void testUpdateTransaction_EmptyCustomerId() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        TransactionDto dto = TransactionDto.builder().customerId(null).transactionAmount(120.1).date(LocalDate.now().minusMonths(2).format(formatter)).build();
        BaseResponse response = restTemplate.patchForObject("http://localhost:" + port + "/transactions/update/1", dto, BaseResponse.class);

        assertNotNull(response);
        assertNotNull(response.getResult());
        assertFalse(response.getResult().getSuccess());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_CUSTOMER_ID.getErrorCode(), response.getResult().getErrorCode());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_CUSTOMER_ID.getErrorMessage(), response.getResult().getErrorMessage());
    }

    @Test
    void testUpdateTransaction_NegativeAmount() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        TransactionDto dto = TransactionDto.builder().customerId(1).transactionAmount(-120.1).date(LocalDate.now().minusMonths(2).format(formatter)).build();
        BaseResponse response = restTemplate.patchForObject("http://localhost:" + port + "/transactions/update/1", dto, BaseResponse.class);

        assertNotNull(response);
        assertNotNull(response.getResult());
        assertFalse(response.getResult().getSuccess());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_AMOUNT.getErrorCode(), response.getResult().getErrorCode());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_AMOUNT.getErrorMessage(), response.getResult().getErrorMessage());
    }

    @Test
    void testUpdateTransaction_EmptyAmount() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        TransactionDto dto = TransactionDto.builder().customerId(1).transactionAmount(null).date(LocalDate.now().minusMonths(2).format(formatter)).build();
        BaseResponse response = restTemplate.patchForObject("http://localhost:" + port + "/transactions/update/1", dto, BaseResponse.class);

        assertNotNull(response);
        assertNotNull(response.getResult());
        assertFalse(response.getResult().getSuccess());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_AMOUNT.getErrorCode(), response.getResult().getErrorCode());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_AMOUNT.getErrorMessage(), response.getResult().getErrorMessage());
    }

    @Test
    void testUpdateTransaction_IncorrectDate() {
        TransactionDto dto = TransactionDto.builder().customerId(1).transactionAmount(120.1).date("2023.11.11").build();
        BaseResponse response = restTemplate.patchForObject("http://localhost:" + port + "/transactions/update/1", dto, BaseResponse.class);

        assertNotNull(response);
        assertNotNull(response.getResult());
        assertFalse(response.getResult().getSuccess());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_DATE.getErrorCode(), response.getResult().getErrorCode());
        assertEquals(ExceptionType.INVALID_ADD_TRANSACTION_DTO_DATE.getErrorMessage(), response.getResult().getErrorMessage());
    }
}
