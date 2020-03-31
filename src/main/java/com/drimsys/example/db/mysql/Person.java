package com.drimsys.example.db.mysql;

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
@Table(name = "PERSON")
public class Person {
    @Id
    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "AGE")
    private int age;

    protected Person(){}

    @Override
    public String toString() {
        return new Gson().toJson(this, this.getClass());
    }
}
