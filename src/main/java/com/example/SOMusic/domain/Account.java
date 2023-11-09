package com.example.SOMusic.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "account")
@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class Account implements Serializable {

    @Id
    @Column(name = "user_id", updatable = false)
    private String userId;

    @Column(name = "user_name")
    private String userName;

    private String password;
    private String address;
    private String zipcode;
    private String phone;
    private String email;
    private double rate;

    public boolean matchesZipcode() {
        return zipcode.matches("^\\d{5}$");
    }

    public boolean matchesPhone() {
        return phone.matches("^01([0-9])(\\d{3,4})(\\d{4})$");
    }
}
