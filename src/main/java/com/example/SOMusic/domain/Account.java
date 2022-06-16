package com.example.SOMusic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name="account") 
@Getter @Setter @ToString
@SuppressWarnings("serial")
public class Account implements Serializable {

	@Id @Column(name="user_id")
	private String userId;
	
	@Column(name="user_name")
	private String userName;
	
	private String password;
	private String address;
	private String zipcode;
	private String phone;
	private String email;
	private double rate;
}
