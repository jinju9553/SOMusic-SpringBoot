package com.example.SOMusic.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="WISHGROUPPURCHASE")
@Getter @Setter @ToString
@SuppressWarnings("serial")
@IdClass(PKWishGP.class)
public class WishGroupPurchase implements Serializable {
	
	@Id
	@Column(name="user_id")
	private String userId;
	
	@Id
	@Column(name="grouppurchase_id")
	private int gpId;
	
	public WishGroupPurchase() {}
	
	public WishGroupPurchase(String userId, int gpId) {
		this.userId = userId;
		this.gpId = gpId;
	}
	
	@MapsId()
	@OneToOne
	@JoinColumn(name="grouppurchase_id", nullable=true)
	private GroupPurchase gp;

}
