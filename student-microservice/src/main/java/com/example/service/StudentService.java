package com.example.service;

import com.example.entity.Student;
import com.example.feignclients.AddressFeignClient;
import com.example.repository.StudentRepository;
import com.example.request.CreateStudentRequest;
import com.example.response.AddressResponse;
import com.example.response.StudentResponse;
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
            // studentResponse.setAddressResponse(getAddressById(student.getAddressId()));
            studentResponse.setAddressResponse(addressFeignClient.getById(student.getAddressId()).getBody());

            return studentResponse;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create student: " + e.getMessage());
        }
    }

    public StudentResponse getById (long id) {
        try {
            Student student = studentRepository.findById(id).orElseThrow();
            StudentResponse studentResponse = new StudentResponse(student);
            studentResponse.setAddressResponse(addressFeignClient.getById(student.getAddressId()).getBody());
            return studentResponse;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get student by ID: " + e.getMessage());
        }
    }

    public AddressResponse getAddressById(long addressId) {
        try {
            Mono<AddressResponse> addressResponseMono = webClient.get().uri("/getById/" + addressId).retrieve()
                    .bodyToMono(AddressResponse.class);

            return addressResponseMono.block();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get address by ID: " + e.getMessage());
        }
    }

}
