package com.example.apigatewayservice.businesslayer;

import com.example.apigatewayservice.presentationlayer.customerdto.CustomerRequestModel;
import com.example.apigatewayservice.presentationlayer.customerdto.CustomerResponseModel;

import java.util.List;

public interface CustomerService {
    List<CustomerResponseModel> getCustomers();

    CustomerResponseModel getCustomerByCustomerId(String customerId);

    CustomerResponseModel addCustomer(CustomerRequestModel customerRequestModel);

    CustomerResponseModel updateCustomer(String customerId, CustomerRequestModel customerRequestModel);


    String deleteCustomerByCustomerId(String customerId);
}
