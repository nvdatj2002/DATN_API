package com.example.DATN_API.Service;



import java.util.List;
import java.util.Optional;

import com.example.DATN_API.Entity.StatusEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DATN_API.Reponsitories.StatusReponsitory;

@Service
public class StatusService {
	@Autowired
	StatusReponsitory StatusReponsitory;

	public List<StatusEntity> findAll() {
		return StatusReponsitory.findAll();
	}

	public StatusEntity findById(int id) {
		Optional<StatusEntity> Status = StatusReponsitory.findById(id);
		return Status.get();
	}
	
	public void createStatus(StatusEntity Status) {
		StatusReponsitory.save(Status);
	}
	
	public void updateStatus(int id,StatusEntity Status) {
		Status.setId(id);
		StatusReponsitory.save(Status);
	}
	
	public void deleteStatus(int id) {
		StatusReponsitory.deleteById(id);
	}

	public Boolean existsById(Integer id) {
		return StatusReponsitory.existsById(id) ? true : false;
	}
}
