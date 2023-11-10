package com.example.feignclients;

import com.example.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.NoSuchElementException;

@FeignClient(url="${address.webservice.url}", value = "address-feign-client",
        path = "/api/address")
public interface AddressFeignClient {

    @GetMapping("/getById/{id}")
    public ResponseEntity<AddressResponse> getById(@PathVariable long id);
}