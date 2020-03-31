package com.drimsys.example.db.oracle;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ORACLE_USERS")
public class User {
    @Id
    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private int age;

    protected User(){}

    @Override
    public String toString() {
        return new Gson().toJson(this, this.getClass());
    }
}
