package com.platzi.pizza.service;

import com.platzi.pizza.persistence.entity.CustomerEntity;
import com.platzi.pizza.persistence.repository.CustomerRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRespository customerRespository;

    @Autowired
    public CustomerService(CustomerRespository customerRespository) {
        this.customerRespository = customerRespository;
    }

    public CustomerEntity findByPhone(String phone){
        return this.customerRespository.findByPhone(phone);
    }
}
