package com.example.DATN_API.Service;

import java.util.List;
import java.util.Optional;

import com.example.DATN_API.Entity.CategoryEntity;
import com.example.DATN_API.Entity.CategoryItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DATN_API.Reponsitories.CategoryItemReponsitory;
import com.example.DATN_API.Reponsitories.CategoryReponsitory;

@Service
public class CategoryService {
	@Autowired
	CategoryReponsitory CategoryReponsitory;
	@Autowired
	CategoryItemReponsitory CategoryItemReponsitory;

	public List<CategoryEntity> findAllCategory() {
		return CategoryReponsitory.findAll();
	}

	public CategoryEntity findByIdCategory(int id) {
		Optional<CategoryEntity> CategoryEntity = CategoryReponsitory.findById(id);
		return CategoryEntity.get();
	}

	public void createCategory(CategoryEntity CategoryEntity) {
		CategoryReponsitory.save(CategoryEntity);
	}

	public void updateCategory(int id, CategoryEntity CategoryEntity) {
		CategoryEntity.setId(id);
		CategoryReponsitory.save(CategoryEntity);
	}

	public void deleteCategory(int id) {
		CategoryReponsitory.deleteById(id);
	}

	public Boolean existsByIdCategory(Integer id) {

		return CategoryReponsitory.existsById(id) ? true : false;
	}

	// CategoryEntityItem
	public List<CategoryItemEntity> findAllCategoryItem() {
		return CategoryItemReponsitory.findAll();
	}

	public CategoryItemEntity findByIdCategoryItem(int id) {
		Optional<CategoryItemEntity> CategoryEntity = CategoryItemReponsitory.findById(id);
		return CategoryEntity.get();
	}

	public void createCategoryItem(CategoryItemEntity CategoryEntity) {
		CategoryItemReponsitory.save(CategoryEntity);
	}

	public void updateCategoryItem(int id, CategoryItemEntity CategoryEntityItem) {
		CategoryEntityItem.setId(id);
		CategoryItemReponsitory.save(CategoryEntityItem);
	}

	public void deleteCategoryItem(int id) {
		CategoryItemReponsitory.deleteById(id);
	}

	public Boolean existsByIdCategoryItem(Integer id) {
		return CategoryItemReponsitory.existsById(id) ? true : false;
	}
}
