package com.apriadchenko.rewardsprogram.repository;

import com.apriadchenko.rewardsprogram.entity.Customer;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Hidden
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
