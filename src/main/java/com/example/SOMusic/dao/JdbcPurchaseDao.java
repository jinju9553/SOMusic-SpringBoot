package com.example.SOMusic.dao;

import com.example.SOMusic.domain.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPurchaseDao implements PurchaseDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_SQL =
            "INSERT INTO purchase (purchase_id, consumer_id, consumer_name, total_amount, address, " +
                    "zipcode, phone, location, shipping_method, shipping_request, product_id) "
                    + "VALUES (sequence_purchase.nextval, :consumer_id, :consumer_name, :total_amount, :address, " +
                    ":zipcode, :phone, :location, :shipping_method, :shipping_request, :product_id)";

    @Override
    public void createPurchase(Purchase purchase) {
        jdbcTemplate.update(INSERT_SQL,
                new Object[]{
                        purchase.getConsumerId(),
                        purchase.getConsumerName(),
                        purchase.getTotalAmount(),
                        purchase.getAddress(),
                        purchase.getZipcode(),
                        purchase.getPhone(),
                        purchase.getLocation(),
                        purchase.getShippingMethod(),
                        purchase.getShippingRequest(),
                        purchase.getProduct().getProductId()});
    }

    private static final String UPDATE_SQL = "UPDATE purchase "
            + "SET consumer_name = :consumer_name, "
            + "	phone = :phone, "
            + "	zipcode = :zipcode, "
            + "	address = :address, "
            + "	shipping_request = :shipping_request "
            + "WHERE purchase_id = :purchase_id";

    @Override
    public void updatePurchase(Purchase purchase) {
        jdbcTemplate.update(UPDATE_SQL,
                new Object[]{
                        purchase.getConsumerName(),
                        purchase.getPhone(),
                        purchase.getZipcode(),
                        purchase.getAddress(),
                        purchase.getShippingRequest(),
                        purchase.getPurchaseId()});
    }
}
