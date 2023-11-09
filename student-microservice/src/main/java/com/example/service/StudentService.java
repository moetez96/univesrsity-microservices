package com.example.service;

import com.example.entity.Student;
import com.example.repository.StudentRepository;
import com.example.request.CreateStudentRequest;
import com.example.response.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public StudentResponse createStudent(CreateStudentRequest createStudentRequest) {

        Student student = new Student();
        student.setFirstName(createStudentRequest.getFirstName());
        student.setLastName(createStudentRequest.getLastName());
        student.setEmail(createStudentRequest.getEmail());

        student.setAddress(createStudentRequest.getAddressId());
        student = studentRepository.save(student);

        return new StudentResponse(student);
    }

    public StudentResponse getById (long id) {
        return new StudentResponse(studentRepository.findById(id).get());
    }

}
