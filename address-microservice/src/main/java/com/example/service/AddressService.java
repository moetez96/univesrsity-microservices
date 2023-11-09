package com.example.service;

import com.example.entity.Address;
import com.example.repository.AddressRepository;
import com.example.request.CreateAddressRequest;
import com.example.response.AddressResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AddressService {

    Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    AddressRepository addressRepository;

    public AddressResponse createAddress(CreateAddressRequest createAddressRequest) {
        try {
            Address address = new Address();
            address.setStreet(createAddressRequest.getStreet());
            address.setCity(createAddressRequest.getCity());

            addressRepository.save(address);
            return new AddressResponse(address);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create address: " + e.getMessage());
        }
    }

    public AddressResponse getById(long id) {
        try {
            logger.info("Info getById " + id);

            Address address = addressRepository.findById(id).orElseThrow();
            return new AddressResponse(address);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Address not found for ID: " + id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get address by ID: " + e.getMessage());
        }
    }
}
