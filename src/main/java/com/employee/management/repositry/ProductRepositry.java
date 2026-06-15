package com.employee.management.repositry;

import com.employee.management.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ProductRepositry extends JpaRepository<Product, Long> {
    @Query("""
           select p from Product p where(p.active=true and 
                   (lower(p.name)like lower(concat('%',:key,'%')) or
                           lower(p.category)like lower(concat('%',:key,'%') ) ))
        """)
   List<Product> searchProducts(String key);
}
