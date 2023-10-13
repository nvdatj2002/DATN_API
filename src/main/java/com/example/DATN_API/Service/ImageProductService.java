package com.example.DATN_API.Service;

import java.util.List;
import java.util.Optional;

import com.example.DATN_API.Entity.ImageProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DATN_API.Reponsitories.ImageProductReponsitory;

@Service
public class ImageProductService {
	@Autowired
	ImageProductReponsitory ImageProductReponsitory;

	public List<ImageProductEntity> findAll() {
		return ImageProductReponsitory.findAll();
	}

	public ImageProductEntity findById(int id) {
		Optional<ImageProductEntity> ImageProductEntity = ImageProductReponsitory.findById(id);
		return ImageProductEntity.get();
	}

	public void createImageProduct(ImageProductEntity ImageProductEntity) {
		ImageProductReponsitory.save(ImageProductEntity);
	}

	public void updateImageProduct(int id, ImageProductEntity ImageProductEntity) {
		ImageProductEntity.setId(id);
		ImageProductReponsitory.save(ImageProductEntity);
	}

	public void deleteImageProduct(int id) {
		ImageProductReponsitory.deleteById(id);
	}

	public Boolean existsById(Integer id) {
		return ImageProductReponsitory.existsById(id) ? true : false;
	}
}
