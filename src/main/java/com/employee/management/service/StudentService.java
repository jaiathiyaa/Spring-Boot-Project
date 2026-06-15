package com.employee.management.service;

import com.employee.management.dto.AddressDTO;
import com.employee.management.dto.StudentRequest;
import com.employee.management.dto.StudentResponse;
import com.employee.management.model.Address;
import com.employee.management.model.Student;
import com.employee.management.repositry.StudentRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepositry studentRepositry;

    public List<StudentResponse> getAllStudent(){
        return studentRepositry.findAll().stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }
    public String addStudent(StudentRequest request) {
        Student student = mapToUserRequest(request);
        studentRepositry.save(student);
        return "Added Successfully";
    }
    public StudentResponse getStudent(Long id){
            return studentRepositry.findById(id).map(this::mapToUserResponse).orElse(null);

    }

    public StudentResponse updateStudent(Long id, StudentRequest s1) {
         Student student = studentRepositry.findById(id).orElse(null);
         if(student == null){
             return null;
         }
        student.setFirstName(s1.getFirstName());
        student.setLastName(s1.getLastName());
        student.setEmail(s1.getEmail());
        student.setPhone(s1.getPhone());
        student.setUserRole(s1.getUserRole());
        if(s1.getAddress() != null){
            Address address = new Address();
            address.setCity(s1.getAddress().getCity());
            address.setCountry(s1.getAddress().getCountry());
            address.setState(s1.getAddress().getState());
            address.setStreet(s1.getAddress().getStreet());
            address.setZipcode(s1.getAddress().getZipcode());
            student.setAddress(address);
        }
         studentRepositry.save(student);
         return mapToUserResponse(student);
    }


    public boolean deleteStudent(Long id) {
        if(studentRepositry.existsById(id)){
            studentRepositry.deleteById(id);
            return true;
        }
        return false;
    }

    private StudentResponse mapToUserResponse(Student s){
        StudentResponse response = new StudentResponse();
        response.setId(s.getId());
        response.setFirstName(s.getFirstName());
        response.setLastName(s.getLastName());
        response.setPhone(s.getPhone());
        response.setEmail(s.getEmail());
        if(s.getAddress() != null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setCity(s.getAddress().getCity());
            addressDTO.setCountry(s.getAddress().getCountry());
            addressDTO.setStreet(s.getAddress().getStreet());
            addressDTO.setState(s.getAddress().getState());
            addressDTO.setZipcode(s.getAddress().getZipcode());
            response.setAddress(addressDTO);
        }
        return response;
    }
    private Student mapToUserRequest(StudentRequest request) {
        Student student = new Student();

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setPhone(request.getPhone());
        student.setEmail(request.getEmail());
        student.setUserRole(request.getUserRole());
        if (request.getAddress() != null) {
            Address address = new Address();

            address.setStreet(request.getAddress().getStreet());
            address.setCity(request.getAddress().getCity());
            address.setState(request.getAddress().getState());
            address.setCountry(request.getAddress().getCountry());
            address.setZipcode(request.getAddress().getZipcode());

            student.setAddress(address);
        }

        return student;
    }


}
