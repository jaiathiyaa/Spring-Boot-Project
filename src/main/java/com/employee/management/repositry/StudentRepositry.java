package com.employee.management.repositry;

import com.employee.management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepositry  extends JpaRepository<Student, Long> {
}
