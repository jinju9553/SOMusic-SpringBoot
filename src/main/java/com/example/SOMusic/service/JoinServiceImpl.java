package com.example.SOMusic.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SOMusic.dao.JoinDao;
import com.example.SOMusic.domain.Join;
import com.example.SOMusic.repository.JoinRepository;

@Service
public class JoinServiceImpl implements JoinService{

	@Autowired
	private JoinRepository joinRepository;
	
	@Autowired
	private JoinDao joinDao;
	
	@Override
	public void registerJoin(Join join) {
		joinDao.createJoin(join);
	}

	@Override
	public List<Join> findAllByUserId(String consumerId) {
		return joinRepository.findAllByConsumerId(consumerId);
	}

	@Override
	public void modifyJoin(Join join) {
		joinDao.updateJoin(join);
	}

	@Transactional
	@Override
	public void deleteJoin(String joinId) {
		joinRepository.deleteByJoinId(joinId);
	}

	@Override
	public Join findJoinByJoinId(int joinId) {
		return joinRepository.findJoinByJoinId(joinId);
	}
}
