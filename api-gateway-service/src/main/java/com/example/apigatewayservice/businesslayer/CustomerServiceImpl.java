package com.example.apigatewayservice.businesslayer;

import com.example.apigatewayservice.domainclientlayer.CustomerServiceClient;
import com.example.apigatewayservice.presentationlayer.customerdto.CustomerRequestModel;
import com.example.apigatewayservice.presentationlayer.customerdto.CustomerResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerServiceClient customerServiceClient;

    public CustomerServiceImpl(CustomerServiceClient customerServiceClient) {
        this.customerServiceClient = customerServiceClient;
    }

    @Override
    public List<CustomerResponseModel> getCustomers() {
        return customerServiceClient.getAllCustomers();
    }



    @Override
    public CustomerResponseModel getCustomerByCustomerId(String customerId) {
        return customerServiceClient.getCustomerById(customerId);
    }

    @Override
    public CustomerResponseModel addCustomer(CustomerRequestModel customerRequestModel) {
        return customerServiceClient.addCustomer(customerRequestModel);
    }

    @Override
    public CustomerResponseModel updateCustomer(String customerId, CustomerRequestModel customerRequestModel) {
        return customerServiceClient.updateCustomer(customerId, customerRequestModel);
    }



    @Override
    public String deleteCustomerByCustomerId(String customerId) {
        return customerServiceClient.deleteCustomerById(customerId);
    }

}
