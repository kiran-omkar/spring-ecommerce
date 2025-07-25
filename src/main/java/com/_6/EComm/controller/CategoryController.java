package com._6.EComm.controller;

import com._6.EComm.dto.CategoryRequest;
import com._6.EComm.dto.CategoryResponse;
import com._6.EComm.service.CategoryService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public List<CategoryResponse> getAllCategories() {
    return categoryService.listAll();
  }

  @PostMapping
  public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest category) {
    return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(category));
  }

  @PutMapping("/{id}")
  public CategoryResponse update(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest) {
    return categoryService.update(id, categoryRequest);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    categoryService.delete(id);
  }

  // @GetMapping("/{id}")
  // public Category getCategoryById(@PathVariable Long id) {
  //   return categoryService.getCategoryById(id);
  // }

  // @GetMapping("/{id}/products")
  // public List<Product> getProductsByCategory(@PathVariable Long id) {
  //   Category category = categoryService.getCategoryById(id);
  //   return category.getProducts();
  // }
} 