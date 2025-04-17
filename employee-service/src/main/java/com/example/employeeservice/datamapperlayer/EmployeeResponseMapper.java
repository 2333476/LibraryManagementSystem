package com.example.employeeservice.datamapperlayer;





import com.example.employeeservice.datalayer.Employee;
import com.example.employeeservice.presentationlayer.EmployeeResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeResponseMapper {

    @Mapping(target = "employeeId", expression = "java(employee.getEmployeeIdentifier().getEmployeeId())")
    @Mapping(target = "streetAddress", expression = "java(employee.getAddress().getStreetAddress())")
    @Mapping(target = "province", expression = "java(employee.getAddress().getProvince())")
    @Mapping(target = "postalCode", expression = "java(employee.getAddress().getPostalCode())")
    @Mapping(target = "country", expression = "java(employee.getAddress().getCountry())")
    @Mapping(target = "city", expression = "java(employee.getAddress().getCity())")
    public EmployeeResponseModel entityToResponseModel(Employee employee);


    public  List<EmployeeResponseModel>    entityToResponseModelList(List<Employee> employees);
}
