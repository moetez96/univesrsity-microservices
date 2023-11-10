package com.example.service;

import com.example.entity.Student;
import com.example.feignclients.AddressFeignClient;
import com.example.repository.StudentRepository;
import com.example.request.CreateStudentRequest;
import com.example.response.AddressResponse;
import com.example.response.StudentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    WebClient webClient;

    @Autowired
    AddressFeignClient addressFeignClient;

    public StudentResponse createStudent(CreateStudentRequest createStudentRequest) {
        try {
            Student student = new Student();
            student.setFirstName(createStudentRequest.getFirstName());
            student.setLastName(createStudentRequest.getLastName());
            student.setEmail(createStudentRequest.getEmail());

            student.setAddressId(createStudentRequest.getAddressId());
            student = studentRepository.save(student);

            StudentResponse studentResponse = new StudentResponse(student);
            studentResponse.setAddressResponse(getAddressById(student.getAddressId()));

            return studentResponse;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create student: " + e.getMessage());
        }
    }

    public StudentResponse getById (long id) {
        try {
            Student student = studentRepository.findById(id).orElseThrow();
            StudentResponse studentResponse = new StudentResponse(student);

            studentResponse.setAddressResponse(getAddressById(student.getAddressId()));

            return studentResponse;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get student by ID: " + e.getMessage());
        }
    }

    @CircuitBreaker(name = "addressMicroService", fallbackMethod = "fallbackGetAddressById")
    public AddressResponse getAddressById(long addressId) {
        try {
            return addressFeignClient.getById(addressId).getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get address by ID: " + e.getMessage());
        }
    }

    public AddressResponse fallbackGetAddressById(long addressId, Throwable throwable) {
        return new AddressResponse();
    }

}
