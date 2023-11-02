package com.example.DATN_API.Service;

import java.util.List;
import java.util.Optional;

import com.example.DATN_API.Entity.Account;
import com.example.DATN_API.Entity.Category;
import com.example.DATN_API.Entity.CategoryItem;
import com.example.DATN_API.Reponsitories.AccountReponsitory;
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
    @Autowired
    AccountReponsitory accountReponsitory;

    public List<Category> findAllCategory() {
        return CategoryReponsitory.findAll();
    }

    public Category findByIdCategory(int id) {
        Optional<Category> category = CategoryReponsitory.findById(id);
        return category.get();
    }

    public Category createCategory(Category Category) {
        return CategoryReponsitory.save(Category);
    }

    public Category updateCategory(Category Category) {
        return CategoryReponsitory.save(Category);
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

    public CategoryItem createCategoryItem(CategoryItem Category) {
        return CategoryItemReponsitory.save(Category);
    }

    public CategoryItem updateCategoryItem(CategoryItem CategoryItem) {
        return CategoryItemReponsitory.save(CategoryItem);
    }

    public void deleteCategoryItem(int id) {
        CategoryItemReponsitory.deleteById(id);
    }

    public Boolean existsByIdCategoryItem(Integer id) {
        return CategoryItemReponsitory.existsById(id) ? true : false;
    }

    public Account findAccountById(int id) {
        Optional<Account> newaccount = accountReponsitory.findById(id);
        return newaccount.get();
    }
}
