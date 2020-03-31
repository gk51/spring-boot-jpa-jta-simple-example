package com.drimsys.example.db.h2;

import com.google.gson.Gson;
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

    @Override
    public String toString() {
        return new Gson().toJson(this, this.getClass());
    }
}
