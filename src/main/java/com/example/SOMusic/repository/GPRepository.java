package com.example.SOMusic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SOMusic.controller.GPRequest;

public interface GPRepository extends JpaRepository<GPRequest, Integer> {

	public GPRequest findByGpId(Integer id);
	
	public List<GPRequest> findBySellerId(String sellerId);
}
