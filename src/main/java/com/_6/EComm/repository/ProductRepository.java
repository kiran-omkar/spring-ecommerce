package com._6.EComm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com._6.EComm.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("""
        SELECT p FROM Product p
        JOIN p.category c
        WHERE (:name IS NULL OR lower(p.name) LIKE lower(concat('%', :name, '%')))
          AND (:category IS NULL OR lower(c.name) = lower(:category))
        """)
        Page<Product> searchByNameAndCategory(@Param("name") String name, @Param("category") String category, Pageable pageable);
    // Page<Product> searchByNameAndCategory(@Param("name") String name, @Param("category") String category, Pageable pageable);
}
