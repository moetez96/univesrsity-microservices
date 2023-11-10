package com.example.service;

import com.example.feignclients.AddressFeignClient;
import com.example.response.AddressResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    Logger logger = LoggerFactory.getLogger(CommonService.class);

    long count = 1;

    @Autowired
    AddressFeignClient addressFeignClient;

    @CircuitBreaker(name = "addressMicroService", fallbackMethod = "fallbackGetAddressById")
    public AddressResponse getAddressById(long addressId) {
        try {
            logger.info("count = " + count);
            count++;
            return addressFeignClient.getById(addressId).getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get address by ID: " + e.getMessage());
        }
    }

    public AddressResponse fallbackGetAddressById(long addressId, Throwable throwable) {
        logger.info("error = " + throwable);
        return new AddressResponse();
    }
}
