package com._6.EComm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com._6.EComm.dto.ProductRequest;
import com._6.EComm.dto.ProductResponse;

public interface ProductService {
    ProductResponse create(ProductRequest productReq);
    ProductResponse update(Long id, ProductRequest req);
    void delete(Long id);
    Page<ProductResponse> list(Pageable pageable);
    Page<ProductResponse> searchByName(String name, Pageable pageable);
    Page<ProductResponse> searchByCategory(String category, Pageable pageable);
}
