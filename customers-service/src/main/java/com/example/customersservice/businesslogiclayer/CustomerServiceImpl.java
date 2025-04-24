package com.example.customersservice.businesslogiclayer;



import com.example.customersservice.dataaccesslayer.Address;
import com.example.customersservice.dataaccesslayer.Customer;
import com.example.customersservice.dataaccesslayer.CustomerIdentifier;
import com.example.customersservice.dataaccesslayer.CustomerRepository;
import com.example.customersservice.datamapperlayer.CustomerRequestMapper;
import com.example.customersservice.datamapperlayer.CustomerResponseMapper;
import com.example.customersservice.presentationlayer.CustomerRequestModel;
import com.example.customersservice.presentationlayer.CustomerResponseModel;
import com.example.customersservice.utils.exceptions.DuplicateCustomerNameException;
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
        // ✅ NEW DUPLICATE CHECK
        if (customerRepository.existsByFirstNameAndLastName(newCustomerData.getFirstName(), newCustomerData.getLastName())) {
            throw new DuplicateCustomerNameException("Customer with name " + newCustomerData.getFirstName() + " " + newCustomerData.getLastName() + " already exists.");
        }

        String pw1 = newCustomerData.getPassword1();
        String pw2 = newCustomerData.getPassword2();
        if (pw1 == null) pw1 = "";
        if (pw2 == null) pw2 = "";
        if (!pw1.equals(pw2)) {
            throw new IllegalArgumentException("Entered passwords do not match!");
        }

        if (newCustomerData.getStreetAddress() == null ||
                newCustomerData.getCity() == null ||
                newCustomerData.getProvince() == null ||
                newCustomerData.getPostalCode() == null) {
            throw new IllegalArgumentException("All address fields must be provided.");
        }

        CustomerIdentifier customerIdentifier = new CustomerIdentifier();
        Address newAddress = new Address(newCustomerData.getStreetAddress(),
                newCustomerData.getCity(), newCustomerData.getProvince(),
                newCustomerData.getPostalCode());

        Customer customer = this.customerRequestMapper.requestModelToEntity(
                newCustomerData, customerIdentifier, newAddress);
        customer.setPassword(pw1);

        Customer savedCustomer = this.customerRepository.save(customer);
        return this.customerResponseMapper.entityToResponseModel(savedCustomer);
    }

    @Override
    public CustomerResponseModel updateCustomer(String customerId, CustomerRequestModel newCustomerData) {
        CustomerIdentifier cid = new CustomerIdentifier(customerId);
        Customer foundCustomer = this.customerRepository.findCustomerByCustomerIdentifier(cid);

        if (foundCustomer == null) {
            throw new NotFoundException("Customer with id: " + customerId + " not found in repository.");
        }

        // ✅ DUPLICATE CHECK on name if it's changing
        if (!foundCustomer.getFirstName().equals(newCustomerData.getFirstName()) ||
                !foundCustomer.getLastName().equals(newCustomerData.getLastName())) {
            if (customerRepository.existsByFirstNameAndLastName(newCustomerData.getFirstName(), newCustomerData.getLastName())) {
                throw new DuplicateCustomerNameException("Another customer with name " + newCustomerData.getFirstName() + " " + newCustomerData.getLastName() + " already exists.");
            }
        }

        String pw1 = newCustomerData.getPassword1();
        String pw2 = newCustomerData.getPassword2();
        if (pw1 == null) pw1 = "";
        if (pw2 == null) pw2 = "";
        if (!pw1.equals(pw2)) {
            throw new IllegalArgumentException("Entered passwords do not match!");
        }

        Address newAddress = new Address(newCustomerData.getStreetAddress(),
                newCustomerData.getCity(), newCustomerData.getProvince(),
                newCustomerData.getPostalCode());

        Customer updatedCustomer = this.customerRequestMapper.requestModelToEntity(
                newCustomerData, foundCustomer.getCustomerIdentifier(), newAddress);
        updatedCustomer.setId(foundCustomer.getId());
        updatedCustomer.setPassword(pw1);

        Customer savedCustomer = this.customerRepository.save(updatedCustomer);
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

