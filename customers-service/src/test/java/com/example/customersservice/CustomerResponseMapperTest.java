package com.example.customersservice;

import com.example.customersservice.dataaccesslayer.Address;
import com.example.customersservice.dataaccesslayer.Customer;
import com.example.customersservice.dataaccesslayer.CustomerIdentifier;
import com.example.customersservice.datamapperlayer.CustomerResponseMapper;
import com.example.customersservice.presentationlayer.CustomerResponseModel;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerResponseMapperTest {

    private final CustomerResponseMapper mapper = Mappers.getMapper(CustomerResponseMapper.class);

    @Test
    void test_entityToResponseModel_mapsCorrectly() {
        Address address = new Address("123 Main", "Montreal", "QC", "H1H1H1");
        CustomerIdentifier id = new CustomerIdentifier("cust-123");
        Customer customer = new Customer(id, "Jane", "Doe", "jane@example.com", address);
        customer.setUsername("janedoe");
        customer.setPassword("pw123");

        CustomerResponseModel model = mapper.entityToResponseModel(customer);

        assertEquals("Jane", model.getFirstName());
        assertEquals("Doe", model.getLastName());
        assertEquals("jane@example.com", model.getEmailAddress());
        assertEquals("123 Main", model.getStreetAddress());
        assertEquals("QC", model.getProvince());
        assertEquals("H1H1H1", model.getPostalCode());
        assertEquals("cust-123", model.getCustomerId());
        assertNull(model.getCity()); // champ ignoré, doit rester null
    }

    @Test
    void test_entityListToResponseModelList_mapsListCorrectly() {
        Address address = new Address("456 Elm", "Toronto", "ON", "A1A1A1");
        CustomerIdentifier id = new CustomerIdentifier("cust-456");
        Customer customer = new Customer(id, "Alice", "Smith", "alice@example.com", address);
        customer.setUsername("alicesmith");
        customer.setPassword("pw");

        List<CustomerResponseModel> result = mapper.entityListToResponseModelList(List.of(customer));

        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getFirstName());
        assertEquals("cust-456", result.get(0).getCustomerId());
    }


    @Test
    public void test_fromEntityToModel() {
        Address address = new Address("123 Maple Street", "Montreal", "QC", "H2X 1A4");
        CustomerIdentifier id = new CustomerIdentifier("abc-123");

        Customer customer = new Customer();
        customer.setId(1);
        customer.setCustomerIdentifier(id);
        customer.setCustomerAddress(address);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmailAddress("john@example.com");
        customer.setUsername("johndoe");

        CustomerResponseModel response = mapper.entityToResponseModel(customer);

        assertNotNull(response);
        assertEquals("abc-123", response.getCustomerId());
        assertEquals("123 Maple Street", response.getStreetAddress());
        assertEquals("QC", response.getProvince());
        assertEquals("H2X 1A4", response.getPostalCode());

        // ⚠ city is ignored in mapper config
        assertNull(response.getCity());
    }

    @Test
    public void test_fromEntityListToModelList() {
        Address address1 = new Address("123 A St", "CityA", "QC", "H2X 1A1");
        CustomerIdentifier id1 = new CustomerIdentifier("id-1");
        Customer c1 = new Customer();
        c1.setId(1);
        c1.setCustomerIdentifier(id1);
        c1.setCustomerAddress(address1);
        c1.setFirstName("A");
        c1.setLastName("B");
        c1.setEmailAddress("a@b.com");
        c1.setUsername("ab");

        Address address2 = new Address("456 B St", "CityB", "ON", "M4X 2B2");
        CustomerIdentifier id2 = new CustomerIdentifier("id-2");
        Customer c2 = new Customer();
        c2.setId(2);
        c2.setCustomerIdentifier(id2);
        c2.setCustomerAddress(address2);
        c2.setFirstName("C");
        c2.setLastName("D");
        c2.setEmailAddress("c@d.com");
        c2.setUsername("cd");

        List<CustomerResponseModel> result = mapper.entityListToResponseModelList(List.of(c1, c2));

        assertEquals(2, result.size());
        assertEquals("id-1", result.get(0).getCustomerId());
        assertEquals("id-2", result.get(1).getCustomerId());
    }


}
