package com.apriadchenko.rewardsprogram.repository;

import com.apriadchenko.rewardsprogram.entity.Transaction;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Hidden
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByCustomerIdAndCreatedDateGreaterThan(Integer customerId, LocalDateTime dateTime);
}
