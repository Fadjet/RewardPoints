package com.apriadchenko.rewardsprogram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Integer id;
    @Column
    String firstName;
    @Column
    String lastName;
    @OneToMany(mappedBy="customer")
    private List<Transaction> transactions;
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;
}
