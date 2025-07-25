package com._6.EComm.util;

import org.springframework.stereotype.Component;

import com._6.EComm.dto.CategoryResponse;
import com._6.EComm.dto.ProductResponse;
import com._6.EComm.entity.Category;
import com._6.EComm.entity.Product;

@Component
public class DtoMapper {
    public CategoryResponse toResponse(Category c) {
        return new CategoryResponse(c.getId(), c.getName());
      }
    
      public ProductResponse toResponse(Product p) {
        CategoryResponse c = toResponse(p.getCategory());
        return new ProductResponse(p.getId(), p.getName(), p.getPrice(), p.getDescription(), c);
      }
}
