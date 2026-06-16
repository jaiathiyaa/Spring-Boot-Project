package com.employee.management.repositry;

import com.employee.management.model.CardItem;
import com.employee.management.model.Product;
import com.employee.management.model.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
@Transactional
public interface CardItemRepositry extends JpaRepository<CardItem,Long> {
    CardItem findByStudentAndProduct(Student student, Product product);

    void deleteByStudentIdAndProductId(Long studentId, Long productId);

    List<CardItem> findByStudent(Student student);

    void deleteByStudent(Student student);
}
