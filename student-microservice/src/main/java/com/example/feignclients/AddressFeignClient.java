package com.example.feignclients;

import com.example.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "api-gateway", path = "address-microservice/api/address")
public interface AddressFeignClient {

    @GetMapping("/getById/{id}")
    public ResponseEntity<AddressResponse> getById(@PathVariable long id);
}
