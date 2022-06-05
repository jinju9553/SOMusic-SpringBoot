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
	
	@Override
	public void createJoin(Join join) {
		// TODO Auto-generated method stub
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
