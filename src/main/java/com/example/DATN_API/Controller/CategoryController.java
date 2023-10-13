package com.example.DATN_API.Controller;

import java.util.List;

import com.example.DATN_API.Entity.CategoryEntity;
import com.example.DATN_API.Entity.CategoryItemEntity;
import com.example.DATN_API.Entity.ResponObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DATN_API.Service.CategoryService;

@RestController
@RequestMapping("/api/category")
@CrossOrigin("*")
public class CategoryController {
	@Autowired
	CategoryService CategoryService;

	@GetMapping()
	public ResponseEntity<List<CategoryEntity>> getAll() {
		return new ResponseEntity<>(CategoryService.findAllCategory(), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<CategoryEntity> findById(@PathVariable Integer id) {
		if (CategoryService.existsByIdCategory(id)) {
			return new ResponseEntity<>(CategoryService.findByIdCategory(id), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping()
	public ResponseEntity<ResponObject> create(@RequestBody CategoryEntity Category) {
		CategoryService.createCategory(Category);
		return new ResponseEntity<>(new ResponObject("SUCCESS", "Category has been added.", Category),
				HttpStatus.CREATED);

	}

	@PutMapping("{id}")
	public ResponseEntity<ResponObject> update(@PathVariable Integer id, @RequestBody CategoryEntity Category) {
		if (!CategoryService.existsByIdCategory(id))
			return new ResponseEntity<>(
					new ResponObject("NOT_FOUND", "Category_id: " + id + " does not exists.", Category),
					HttpStatus.NOT_FOUND);

		CategoryService.updateCategory(id, Category);
		return new ResponseEntity<>(new ResponObject("SUCCESS", "Category has been updated.", Category), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<ResponObject> delete(@PathVariable Integer id) {
		if (!CategoryService.existsByIdCategory(id))
			return new ResponseEntity<>(new ResponObject("NOT_FOUND", "Category_id: " + id + " does not exists.", id),
					HttpStatus.NOT_FOUND);
		CategoryService.deleteCategory(id);
		return new ResponseEntity<>(new ResponObject("SUCCESS", "Category has been deleted.", id), HttpStatus.OK);
	}
	
	
	//CategoryItem
	@GetMapping("/categoryItem")
	public ResponseEntity<List<CategoryItemEntity>> getAllCategoryItem() {
		return new ResponseEntity<>(CategoryService.findAllCategoryItem(), HttpStatus.OK);
	}

	@GetMapping("/categoryItem/{id}")
	public ResponseEntity<CategoryItemEntity> findByIdCategoryItem(@PathVariable Integer id) {
		if (CategoryService.existsByIdCategoryItem(id)) {
			return new ResponseEntity<>(CategoryService.findByIdCategoryItem(id), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/categoryItem")
	public ResponseEntity<ResponObject> createCategoryItem(@RequestBody CategoryItemEntity categoryItem) {
		CategoryService.createCategoryItem(categoryItem);
		return new ResponseEntity<>(new ResponObject("SUCCESS", "CategoryItem has been added.", categoryItem),
				HttpStatus.CREATED);
	}

	@PutMapping("/categoryItem/{id}")
	public ResponseEntity<ResponObject> updateCategoryItem(@PathVariable Integer id, @RequestBody CategoryItemEntity Category) {
		if (!CategoryService.existsByIdCategoryItem(id))
			return new ResponseEntity<>(
					new ResponObject("NOT_FOUND", "CategoryItem_id: " + id + " does not exists.", Category),
					HttpStatus.NOT_FOUND);

		CategoryService.updateCategoryItem(id, Category);
		return new ResponseEntity<>(new ResponObject("SUCCESS", "CategoryItem has been updated.", Category), HttpStatus.OK);
	}

	@DeleteMapping("/categoryItem/{id}")
	public ResponseEntity<ResponObject> deleteCategoryItem(@PathVariable Integer id) {
		if (!CategoryService.existsByIdCategoryItem(id))
			return new ResponseEntity<>(new ResponObject("NOT_FOUND", "CategoryItem_id: " + id + " does not exists.", id),
					HttpStatus.NOT_FOUND);
		CategoryService.deleteCategoryItem(id);
		return new ResponseEntity<>(new ResponObject("SUCCESS", "CategoryItem has been deleted.", id), HttpStatus.OK);
	}

}