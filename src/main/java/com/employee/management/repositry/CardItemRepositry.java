package com.employee.management.repositry;

import com.employee.management.model.CardItem;
import com.employee.management.model.Product;
import com.employee.management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardItemRepositry extends JpaRepository<CardItem,Long> {
    CardItem findByStudentAndProduct(Student student, Product product);

    void deleteByStudentIdAndProductId(Student student, Product product);
}
