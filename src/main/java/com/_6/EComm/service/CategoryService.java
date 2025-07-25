package com._6.EComm.service;

import java.util.List;

import com._6.EComm.dto.CategoryRequest;
import com._6.EComm.dto.CategoryResponse;

public interface CategoryService {
  CategoryResponse create(CategoryRequest req);
  CategoryResponse update(Long id, CategoryRequest req);
  void delete(Long id);
  List<CategoryResponse> listAll();
}
