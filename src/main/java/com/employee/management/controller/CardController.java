package com.employee.management.controller;

import com.employee.management.dto.CardItemRequest;
import com.employee.management.model.CardItem;
import com.employee.management.model.Student;
import com.employee.management.repositry.CardItemRepositry;
import com.employee.management.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CardController {
    private final CardService cardService;
    @PostMapping
    public ResponseEntity<String> addCart(@RequestHeader("X-User-ID") String student_id, @RequestBody CardItemRequest request){
        if(!cardService.addtoCard(student_id,request)){
            return ResponseEntity.badRequest().body("Product is Out of Stock or User not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/items/{product_id}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long product_id,@RequestHeader("X-User-ID") String studentId){
        boolean deleted=cardService.deleteItemCard(studentId,product_id);
        return deleted? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/items")
    public ResponseEntity<List<CardItem>> getCardItem(@RequestHeader("X-User-ID") String studentId){
        return ResponseEntity.ok(cardService.getAllCardItems(studentId));
    }
}
