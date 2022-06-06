package com.example.SOMusic.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.SOMusic.domain.Join;

@Repository
public class JdbcJoinDao implements JoinDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private static final String INSERT_SQL = 
			"INSERT INTO join (join_id, shipping_cost, quantity, status, total_amount, consumer_name, address, zipcode, phone, shipping_request, shipping_method, "
			+ "account_holder, refund_account, refund_bank, refund_holder, grouppurchase_id) "
			+ "VALUE (sequence_join.nextval, :shipping_cost, :quantity, :status, :total_amount, :consumer_name, :address, :zipcode, :phone, :shipping_request, :shipping_method, "
			+ ":account_holder, :refund_account, :refund_bank, :refund_holder, :grouppurchase_id)";
	@Override
	public void createJoin(Join join) {
		jdbcTemplate.update(INSERT_SQL,
				new Object[] { 
						join.getJoinId(),
						join.getShippingCost(),
						join.getQuantity(),
						join.getStatus(),
						join.getTotalAmount(),
						join.getConsumerName(),
						join.getAddress(),
						join.getZipcode(),
						join.getPhone(),
						join.getShippingRequest(),
						join.getShippingMethod(),
						//다음 행
						join.getAccountHolder(), 		
						join.getRefundAccount(),
						join.getRefundBank(),
						join.getRefundHolder(),
						join.getGroupPurchase().getGpId()});
	}
	
	private static final String UPDATE_SQL = "UPDATE join "
			+ "SET account_holder = :account_holder, "
			+ "	shipping_method = :shipping_method, "
			+ "	consumer_name = :consumer_name, "
			+ "	phone = :phone, "
			+ "	zipcode = :zipcode, "
			+ "	address = :address, "
			+ "	shipping_request = :shipping_request, "
			+ "	refund_account = :refund_account, "
			+ "	refund_bank = :refund_bank, "
			+ "	refund_holder = :refund_holder "
			+ "WHERE join_id = :join_id"; //세미콜론 넣으면 안됨
	@Override
	public void updateJoin(Join join) {
		jdbcTemplate.update(UPDATE_SQL,
				new Object[] { 
						join.getAccountHolder(), 
						join.getShippingMethod(),
						join.getConsumerName(),
						join.getPhone(),
						join.getZipcode(),
						join.getAddress(),
						join.getShippingRequest(),
						join.getRefundAccount(),
						join.getRefundBank(),
						join.getRefundHolder(),
						join.getJoinId()});
	}
}
