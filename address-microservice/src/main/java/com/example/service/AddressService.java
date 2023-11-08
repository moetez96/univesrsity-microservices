package com.example.service;

import com.example.entity.Address;
import com.example.repository.AddressRepository;
import com.example.request.CreateAddressRequest;
import com.example.response.AddressResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AddressService {

    Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    AddressRepository addressRepository;

    public AddressResponse createAddress(CreateAddressRequest createAddressRequest) {

        Address address = new Address();
        address.setStreet(createAddressRequest.getStreet());
        address.setCity(createAddressRequest.getCity());

        addressRepository.save(address);
        return new AddressResponse(address);
    }

    public AddressResponse getById(long id) {
        logger.info("Info getById " + id);

        Address address = addressRepository.findById(id).get();
        return new AddressResponse(address);
    }
}
