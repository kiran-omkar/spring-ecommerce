package com._6.EComm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com._6.EComm.dto.CategoryRequest;
import com._6.EComm.dto.CategoryResponse;
import com._6.EComm.entity.Category;
import com._6.EComm.exception.EntityNotFoundException;
import com._6.EComm.repository.CategoryRepository;
import com._6.EComm.util.DtoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
  private final CategoryRepository categoryRepository;

  private final DtoMapper mapper = new DtoMapper();

  @Override
  public CategoryResponse create(CategoryRequest req) {
    Category saved = categoryRepository.save(new Category(null, req.name()));
    return mapper.toResponse(saved);
  }

  @Override
  public CategoryResponse update(Long id, CategoryRequest req) {
    Category cat = categoryRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Category", id));
    cat.setName(req.name());
    return mapper.toResponse(categoryRepository.save(cat));
  }

  @Override
  public void delete(Long id) {
    categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category", id));
    categoryRepository.deleteById(id);
  }

  @Override
  public List<CategoryResponse> listAll() {
    return categoryRepository.findAll().stream().map(mapper::toResponse).toList();
  }
  
}
