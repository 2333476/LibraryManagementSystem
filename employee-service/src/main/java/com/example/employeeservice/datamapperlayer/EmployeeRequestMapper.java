package com.example.employeeservice.datamapperlayer;




import com.example.employeeservice.datalayer.Address;
import com.example.employeeservice.datalayer.Employee;
import com.example.employeeservice.presentationlayer.EmployeeRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface EmployeeRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employeeIdentifier", ignore = true)
    Employee requestModelToEntity(EmployeeRequestModel postData,
                                  Address address);
}
