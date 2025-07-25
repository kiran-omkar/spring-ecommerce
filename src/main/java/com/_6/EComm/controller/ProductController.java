package com._6.EComm.controller;

import com._6.EComm.dto.ProductRequest;
import com._6.EComm.dto.ProductResponse;
import com._6.EComm.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  // @GetMapping
  // public Page<ProductResponse> list(
  //   @RequestParam(defaultValue = "0") int page,
  //   @RequestParam(defaultValue = "10") int size,
  //   @RequestParam(defaultValue = "id,asc") String[] sort
  // ) {

  //   List<Sort.Order> orders = Arrays.stream(sort).map(s -> {
  //     String[] parts = s.split(",");
  //     String property = parts[0];
  //     String direction = parts.length > 1 ? parts[1] : "asc";
  //     return new Sort.Order(Sort.Direction.fromString(direction), property);
  //   }).toList();
  //   Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
  
  //   return productService.list(pageable);
  // }
  @GetMapping
  public Page<ProductResponse> list(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
    return productService.list(pageable);
  }

  @GetMapping("/search")
  public Page<ProductResponse> search(
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String category,
    @PageableDefault(size = 10, sort = "id") Pageable pageable) {
    if (name != null) {
      return productService.searchByName(name, pageable);
    }
    if (category != null) {
      return productService.searchByCategory(category, pageable);
    }
    // fallback to all
    return productService.list(pageable);
  }

  @PostMapping
  public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest productRequest) {
      // return productService.createProduct(productRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(productRequest));
  }

  @PutMapping("/{id}")
  public ProductResponse update(
      @PathVariable Long id,
      @Valid @RequestBody ProductRequest req) {
    return productService.update(id, req);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProduct(@PathVariable Long id) {
    productService.delete(id);
  }

  // @GetMapping("/{id}")
  // public ProductResponse getProductById(@PathVariable Long id) {
  //   return productService.getProductById(id);
  // }

  // @GetMapping("/all")
  // public ProductResponse getAllProductsNoPaging() {
  //   return productService.getAllProductsNoPaging();
  // }
} 