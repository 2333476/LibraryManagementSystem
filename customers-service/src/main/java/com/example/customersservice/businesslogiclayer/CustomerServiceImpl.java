package com.example.customersservice.businesslogiclayer;



import com.example.customersservice.dataaccesslayer.Address;
import com.example.customersservice.dataaccesslayer.Customer;
import com.example.customersservice.dataaccesslayer.CustomerIdentifier;
import com.example.customersservice.dataaccesslayer.CustomerRepository;
import com.example.customersservice.datamapperlayer.CustomerRequestMapper;
import com.example.customersservice.datamapperlayer.CustomerResponseMapper;
import com.example.customersservice.presentationlayer.CustomerRequestModel;
import com.example.customersservice.presentationlayer.CustomerResponseModel;
import com.example.customersservice.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerResponseMapper customerResponseMapper;
    private final CustomerRequestMapper customerRequestMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerResponseMapper customerResponseMapper,
                               CustomerRequestMapper customerRequestMapper) {
        this.customerRepository = customerRepository;
        this.customerResponseMapper = customerResponseMapper;
        this.customerRequestMapper = customerRequestMapper;
    }

    @Override
    public List<CustomerResponseModel> getCustomers() {
        List<Customer> customers = this.customerRepository.findAll();
        customers.get(0).getCustomerAddress().getStreetAddress();
        List<CustomerResponseModel> customerResponseModels =
                this.customerResponseMapper.entityListToResponseModelList(customers);
        return customerResponseModels;
    }

    @Override
    public CustomerResponseModel getCustomerbyCustomerId(String customer_id) {
        CustomerIdentifier cid =  new CustomerIdentifier(customer_id);
        Customer customer = this.customerRepository.findCustomerByCustomerIdentifier(cid);
        CustomerResponseModel result = null;
        if (customer == null) {
            throw new NotFoundException("Customer with " + customer_id + " not found.");
        } else {
            result = this.customerResponseMapper.entityToResponseModel(customer);
        }
        return result;
    }

    @Override
    public CustomerResponseModel addCustomer(CustomerRequestModel newCustomerData) {
        String message = ""; Customer savedCustomer = new Customer();
        String pw1 = newCustomerData.getPassword1();
        String pw2 = newCustomerData.getPassword2();
        if (pw1 == null) {
            pw1 = "";
        }
        if (pw2 == null) {
            pw2 = "";
        }
        if (!pw1.equals(pw2)) {
            message = "Entered passwords do not match!";
        } else {
            CustomerIdentifier customerIdentifier = new CustomerIdentifier();
            Address newAddress = new Address(newCustomerData.getStreetAddress(),
                    newCustomerData.getCity(), newCustomerData.getProvince(),
                    newCustomerData.getPostalCode());
            Customer customer = this.customerRequestMapper.requestModelToEntity(
                    newCustomerData, customerIdentifier, newAddress
            );
//                customer.setCustomerIdentifier( customerIdentifier );
            customer.setPassword(newCustomerData.getPassword1());
            savedCustomer = this.customerRepository.save(customer);
            if (savedCustomer != null)
                message = "Customer saved successfully.";
            else
                message = "Could not save new customer into repository.";

        }
        return this.customerResponseMapper.entityToResponseModel(savedCustomer);
    }

    @Override
    public CustomerResponseModel updateCustomer(String customerId,
                                                CustomerRequestModel newCustomerData) {
        String message = ""; Customer savedCustomer = new Customer();
        CustomerIdentifier  cid =  new CustomerIdentifier(customerId);
        Customer foundCustomer = this.customerRepository.findCustomerByCustomerIdentifier(cid);
        if (foundCustomer == null) {
            message = "Customer with id: " + customerId + " not found in repository.";
        } else {
            String pw1 = newCustomerData.getPassword1();
            String pw2 = newCustomerData.getPassword2();
            if (pw1 == null) {
                pw1 = "";
            }
            if (pw2 == null) {
                pw2 = "";
            }
            if (pw1.equals(pw2)) {
                Address newAddress = new Address(newCustomerData.getStreetAddress(),
                        newCustomerData.getCity(), newCustomerData.getProvince(),
                        newCustomerData.getPostalCode());
                Customer customer = this.customerRequestMapper.requestModelToEntity(
                        newCustomerData,
                        foundCustomer.getCustomerIdentifier(),
                        newAddress
                );
                customer.setId(foundCustomer.getId());  // important
                customer.setPassword(newCustomerData.getPassword1());
                 savedCustomer = this.customerRepository.save(customer);
                if (savedCustomer != null)
                    message = "Customer updated successfully.";
                else
                    message = "Could not save new customer into repository.";

            } else
                message = "Entered passwords do not match!";
        }
        return this.customerResponseMapper.entityToResponseModel(savedCustomer);
    }

    @Override

    public String deleteCustomerbyCustomerId(String customerId) {
        String message = "";
        CustomerIdentifier  cid =  new CustomerIdentifier(customerId);
        Customer foundCustomer = this.customerRepository.findCustomerByCustomerIdentifier(cid);
        if (foundCustomer == null) {
            message = "Customer with id: " + customerId + " not found in repository.";
        } else {

            this.customerRepository.delete(foundCustomer);
            message = "Customer with id: " + customerId + " deleted successfully.";
        }
        return message;
    }

    // Helper methods
    public CustomerResponseModel fromEntityToModel(Customer customer) {
        CustomerResponseModel customerResponseModel =
                new CustomerResponseModel();
        customerResponseModel.setFirstName(customer.getFirstName());
        customerResponseModel.setLastName(customer.getLastName());

        return customerResponseModel;
    }

    public List<CustomerResponseModel> fromEntityListToModelList(
            List<Customer> customers) {

        List<CustomerResponseModel> customerResponseModels =
                new ArrayList<>();
        for (Customer c : customers) {
            customerResponseModels.add(fromEntityToModel(c));
        }
        return customerResponseModels;
    }

}

