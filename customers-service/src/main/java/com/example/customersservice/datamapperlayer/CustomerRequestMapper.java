package com.example.customersservice.datamapperlayer;


import com.example.customersservice.dataaccesslayer.Address;
import com.example.customersservice.dataaccesslayer.Customer;
import com.example.customersservice.dataaccesslayer.CustomerIdentifier;
import com.example.customersservice.presentationlayer.CustomerRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerRequestMapper {


//    @Mapping(target = "customerIdentifier", ignore = true)
//    @Mapping(target = "phoneNumbers", ignore = true)
//    @Mapping(target = "customerAddresses", ignore = true)
//@Mapping(target = "password", ignore = true)
@Mapping(target = "customerIdentifier", source = "customerIdentifier")
@Mapping(target = "customerAddress", source = "address")
@Mapping(target = "id", ignore = true)
    Customer requestModelToEntity(CustomerRequestModel customerRequestModel,
                                  CustomerIdentifier customerIdentifier,
                                  Address address);
    List<Customer> requestModelListToEntityList(List<CustomerRequestModel> customerRequestModel);

}
