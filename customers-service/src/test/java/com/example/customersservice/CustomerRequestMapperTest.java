package com.example.customersservice;

import com.example.customersservice.dataaccesslayer.Address;
import com.example.customersservice.dataaccesslayer.Customer;
import com.example.customersservice.dataaccesslayer.CustomerIdentifier;
import com.example.customersservice.dataaccesslayer.PhoneNumber;
import com.example.customersservice.dataaccesslayer.PhoneType;
import com.example.customersservice.datamapperlayer.CustomerRequestMapper;
import com.example.customersservice.presentationlayer.CustomerRequestModel;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRequestMapperTest {

    private final CustomerRequestMapper mapper = Mappers.getMapper(CustomerRequestMapper.class);

    @Test
    void test_requestModelToEntity_withAllFields() {
        CustomerRequestModel model = new CustomerRequestModel(
                "Smith", "Alice", "alice@example.com", "456 Park", "H1H1H1", "Montreal", "QC",
                "alicesmith", "pw123", "pw123",
                List.of(new PhoneNumber(PhoneType.WORK, "514-999-8888"))
        );

        CustomerIdentifier id = new CustomerIdentifier("abc-123");
        Address address = new Address("456 Park", "Montreal", "QC", "H1H1H1");

        Customer customer = mapper.requestModelToEntity(model, id, address);

        assertEquals("Alice", customer.getFirstName());
        assertEquals("Smith", customer.getLastName());
        assertEquals("alice@example.com", customer.getEmailAddress());
        assertEquals("alicesmith", customer.getUsername());
        assertEquals("Montreal", customer.getCustomerAddress().getCity());
        assertEquals(PhoneType.WORK, customer.getPhoneNumbers().get(0).getType());
    }

    @Test
    void test_requestModelListToEntityList() {
        CustomerRequestModel model1 = new CustomerRequestModel(
                "Smith", "Alice", "alice@example.com", "123 St", "H1H1H1", "City", "QC",
                "alicesmith", "pw1", "pw1",
                List.of(new PhoneNumber(PhoneType.HOME, "514-123-0000"))
        );

        CustomerRequestModel model2 = new CustomerRequestModel(
                "Doe", "John", "john@example.com", "456 Ave", "H2H2H2", "Laval", "QC",
                "johndoe", "pw2", "pw2",
                List.of(new PhoneNumber(PhoneType.MOBILE, "514-321-0000"))
        );

        List<Customer> customers = mapper.requestModelListToEntityList(List.of(model1, model2));

        assertEquals(2, customers.size());
        assertEquals("Alice", customers.get(0).getFirstName());
        assertEquals("John", customers.get(1).getFirstName());
    }



}
