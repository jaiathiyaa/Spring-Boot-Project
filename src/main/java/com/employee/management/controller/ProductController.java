package com.employee.management.controller;

import com.employee.management.dto.ProductRequest;
import com.employee.management.dto.ProductResponse;
import com.employee.management.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request){
        return new ResponseEntity<ProductResponse>(productService.addProduct(request),HttpStatus.CREATED);
    }
    @GetMapping("")
    public ResponseEntity<List<ProductResponse>> getProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id , @RequestBody ProductRequest request){
        return productService.updateProduct(id,request).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String key){
        return ResponseEntity.ok(productService.searchProduct(key));
    }
}
