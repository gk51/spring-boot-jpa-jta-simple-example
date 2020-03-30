package com.drimsys.example.domain.h2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CUSTOMER")
public class Customer {
    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private int age;

    protected Customer(){}
}
