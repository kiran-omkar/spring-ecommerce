package com._6.EComm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com._6.EComm.dto.ProductRequest;
import com._6.EComm.dto.ProductResponse;
import com._6.EComm.entity.Category;
import com._6.EComm.entity.Product;
import com._6.EComm.exception.EntityNotFoundException;
import com._6.EComm.repository.CategoryRepository;
import com._6.EComm.repository.ProductRepository;
import com._6.EComm.util.DtoMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  private final DtoMapper mapper = new DtoMapper();

  @Override
  public ProductResponse create(ProductRequest productReq) {
    Category cat = categoryRepository.findById(productReq.categoryId()).orElseThrow(() -> new EntityNotFoundException("Category", productReq.categoryId()));
    Product p = new Product(null, productReq.name(), productReq.price(), cat);
    return mapper.toResponse(productRepository.save(p));
  }

  @Override
  public ProductResponse update(Long id, ProductRequest req) {
    Product p = productRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Product", id));
    Category cat = categoryRepository.findById(req.categoryId())
      .orElseThrow(() -> new EntityNotFoundException("Category", req.categoryId()));
    p.setName(req.name());
    p.setPrice(req.price());
    p.setCategory(cat);
    return mapper.toResponse(productRepository.save(p));
  }

  @Override
  public void delete(Long id) {
    productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product", id));
    productRepository.deleteById(id);
  }

  @Override
  public Page<ProductResponse> list(Pageable pageable) {
    return productRepository.findAll(pageable).map(mapper::toResponse);
  }

  @Override
  public Page<ProductResponse> searchByName(String name, Pageable pageable) {
    return productRepository.findByNameContainingIgnoreCase(name, pageable).map(mapper::toResponse);
  }

  @Override
  public Page<ProductResponse> searchByCategory(String category, Pageable pageable) {
    return productRepository.searchByNameAndCategory(null, category, pageable).map(mapper::toResponse);
  }
  
}
