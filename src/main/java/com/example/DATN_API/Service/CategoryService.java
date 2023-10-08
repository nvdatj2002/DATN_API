package com.example.DATN_API.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DATN_API.Entity.Category;
import com.example.DATN_API.Entity.CategoryItem;
import com.example.DATN_API.Reponsitories.CategoryItemReponsitory;
import com.example.DATN_API.Reponsitories.CategoryReponsitory;

@Service
public class CategoryService {
	@Autowired
	CategoryReponsitory CategoryReponsitory;
	@Autowired
	CategoryItemReponsitory CategoryItemReponsitory;

	public List<Category> findAllCategory() {
		return CategoryReponsitory.findAll();
	}

	public Category findByIdCategory(int id) {
		Optional<Category> Category = CategoryReponsitory.findById(id);
		return Category.get();
	}

	public void createCategory(Category Category) {
		CategoryReponsitory.save(Category);
	}

	public void updateCategory(int id, Category Category) {
		Category.setId(id);
		CategoryReponsitory.save(Category);
	}

	public void deleteCategory(int id) {
		CategoryReponsitory.deleteById(id);
	}

	public Boolean existsByIdCategory(Integer id) {
		return CategoryReponsitory.existsById(id) ? true : false;
	}

	// CategoryItem
	public List<CategoryItem> findAllCategoryItem() {
		return CategoryItemReponsitory.findAll();
	}

	public CategoryItem findByIdCategoryItem(int id) {
		Optional<CategoryItem> Category = CategoryItemReponsitory.findById(id);
		return Category.get();
	}

	public void createCategoryItem(CategoryItem Category) {
		CategoryItemReponsitory.save(Category);
	}

	public void updateCategoryItem(int id, CategoryItem CategoryItem) {
		CategoryItem.setId(id);
		CategoryItemReponsitory.save(CategoryItem);
	}

	public void deleteCategoryItem(int id) {
		CategoryItemReponsitory.deleteById(id);
	}

	public Boolean existsByIdCategoryItem(Integer id) {
		return CategoryItemReponsitory.existsById(id) ? true : false;
	}
}
