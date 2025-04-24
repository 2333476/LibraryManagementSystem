package com.example.customersservice.datamapperlayer;


import com.example.customersservice.dataaccesslayer.Customer;
import com.example.customersservice.presentationlayer.CustomerResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerResponseMapper {
//    @Mapping(target = "phoneType", expression = "java(  customer.getPhoneNumber().getType().toString() )")
//    @Mapping(target = "phoneNumber", expression = "java(  customer.getPhoneNumber().getNumber() )")

    @Mapping(target = "customerId", expression ="java( customer.getCustomerIdentifier().getCustomerId()  )")
    @Mapping(target = "streetAddress", expression = "java(  customer.getCustomerAddress().getStreetAddress() )")
    @Mapping(target = "province", expression = "java(  customer.getCustomerAddress().getProvince() )")
    @Mapping(target = "postalCode", expression = "java(  customer.getCustomerAddress().getPostalCode() )")
    @Mapping(target = "city", ignore = true)
    CustomerResponseModel entityToResponseModel(Customer customer);
    List<CustomerResponseModel> entityListToResponseModelList(List<Customer> customers);





}
