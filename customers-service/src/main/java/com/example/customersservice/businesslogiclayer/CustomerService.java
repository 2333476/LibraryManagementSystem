package com.example.customersservice.businesslogiclayer;




import com.example.customersservice.presentationlayer.CustomerRequestModel;
import com.example.customersservice.presentationlayer.CustomerResponseModel;

import java.util.List;

public interface CustomerService {
    List<CustomerResponseModel> getCustomers();
    CustomerResponseModel getCustomerbyCustomerId(String customer_id);

    CustomerResponseModel addCustomer(CustomerRequestModel newCustomerData);

    CustomerResponseModel updateCustomer(String customerId, CustomerRequestModel newCustomerData);

    String deleteCustomerbyCustomerId(String customerId);
}
