package com.employee.management.service;

import com.employee.management.dto.ProductRequest;
import com.employee.management.dto.ProductResponse;
import com.employee.management.model.Product;
import com.employee.management.repositry.ProductRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepositry productRepositry;
    public ProductResponse addProduct(ProductRequest request){
        Product product = new Product();
        Product saveProduct = mapToProductRequest(product,request);
        productRepositry.save(saveProduct);
        return mapToProductResponse(saveProduct);
    }
    public Product mapToProductRequest(Product product,ProductRequest request){
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        return product;
    }
    public ProductResponse mapToProductResponse(Product product){
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setCategory(product.getCategory());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setImageUrl(product.getImageUrl());
        response.setActive(product.getActive());
        return response;
    }

    public List<ProductResponse> getAllProducts() {
        return productRepositry.findAll().stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    public ProductResponse getProduct(Long id) {
        Product p = productRepositry.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: "+ id));
        return mapToProductResponse(p);
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest request) {
        return productRepositry.findById(id).map((existingProduct) -> {
            Product product =  mapToProductRequest(existingProduct,request);
            productRepositry.save(product);
            return mapToProductResponse(product);
        });

    }

    public String deleteProduct(Long id) {
        Product product = productRepositry.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: "+ id));
        product.setActive(false);
        productRepositry.save(product);
        return "Product Deactivated";
    }

    public List<ProductResponse> searchProduct(String key) {
        return productRepositry.searchProducts(key).stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }
}

