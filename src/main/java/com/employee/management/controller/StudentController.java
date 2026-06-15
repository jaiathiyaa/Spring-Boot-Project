package com.employee.management.controller;
import com.employee.management.dto.StudentRequest;
import com.employee.management.dto.StudentResponse;
import com.employee.management.model.Student;
import com.employee.management.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StudentController {
    private final StudentService studentService;


    @GetMapping("/students")
    public ResponseEntity<List<StudentResponse>> getAllStudents(){
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @PostMapping("/students")
    public ResponseEntity<String> addStudents(@RequestBody StudentRequest s1){
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.addStudent(s1));
    }
    @GetMapping("/students/{id}")
    public ResponseEntity<StudentResponse> getStudent(@PathVariable Long id){
        StudentResponse s = studentService.getStudent(id);
        if(s != null){
            return ResponseEntity.ok(s);
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/students/{id}")
    public ResponseEntity<StudentResponse> updateStudents(@PathVariable Long id,@RequestBody StudentRequest s1) {
        StudentResponse response = studentService.updateStudent(id, s1);
        if (response != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @DeleteMapping("/students/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable long id){
        boolean deleted = studentService.deleteStudent(id);
        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
