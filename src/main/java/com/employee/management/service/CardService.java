package com.employee.management.service;

import com.employee.management.dto.CardItemRequest;
import com.employee.management.dto.ProductResponse;
import com.employee.management.model.CardItem;
import com.employee.management.model.Product;
import com.employee.management.model.Student;
import com.employee.management.repositry.CardItemRepositry;
import com.employee.management.repositry.ProductRepositry;
import com.employee.management.repositry.StudentRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CardService {
    private final ProductRepositry productRepositry;
    private final StudentRepositry studentRepositry;
    private final CardItemRepositry cardItemRepositry;


    public Boolean addtoCard(String studentId, CardItemRequest request) {
        Optional<Product> productOpt = productRepositry.findById(request.getProductId());
        if(productOpt.isEmpty()){
            return false;
        }
        Product product = productOpt.get();
        if(product.getStockQuantity() < request.getQuantity()){
            return false;
        }
        Optional<Student> studentOpt = studentRepositry.findById(Long.valueOf(studentId));
        if(studentOpt.isEmpty()){
            return false;
        }
        Student student = studentOpt.get();
        CardItem existingCardItem = cardItemRepositry.findByStudentAndProduct(student,product);

        if(existingCardItem!=null){
            existingCardItem.setQuantity(existingCardItem.getQuantity()+request.getQuantity());
            existingCardItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCardItem.getQuantity())));
            cardItemRepositry.save(existingCardItem);
        }
        else{
            CardItem cardItem = new CardItem();
            cardItem.setStudent(student);
            cardItem.setProduct(product);
            cardItem.setQuantity(request.getQuantity());
            cardItem.setPrice(product.getPrice());
            cardItemRepositry.save(cardItem);
        }
        return true;

    }

    public boolean deleteItemCard(String studentId, Long productId) {
        Optional<Product> productOpt = productRepositry.findById(productId);
        Optional<Student> studentOpt = studentRepositry.findById(Long.valueOf(studentId));
        if(studentOpt.isPresent() && productOpt.isPresent()){
            cardItemRepositry.deleteByStudentIdAndProductId(studentOpt.get().getId(),productOpt.get().getId());
            return true;
        }
        return false;
    }

    public List<CardItem> getAllCardItems(String studentId) {
        return studentRepositry.findById(Long.valueOf(studentId))
                .map(cardItemRepositry::findByStudent)
                .orElseGet(List::of);
    }
}
