package com.example.employeeservice.businesslayer;



import com.example.employeeservice.datalayer.Address;
import com.example.employeeservice.datalayer.Employee;
import com.example.employeeservice.datalayer.EmployeeIdentifier;
import com.example.employeeservice.datalayer.EmployeeRepository;
import com.example.employeeservice.datamapperlayer.EmployeeRequestMapper;
import com.example.employeeservice.datamapperlayer.EmployeeResponseMapper;
import com.example.employeeservice.presentationlayer.EmployeeRequestModel;
import com.example.employeeservice.presentationlayer.EmployeeResponseModel;
import com.example.employeeservice.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeServiceImpl implements  EmployeeService{

    private EmployeeRepository employeeRepository;
    private EmployeeRequestMapper employeeRequestMapper;
    private EmployeeResponseMapper employeeResponseMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               EmployeeRequestMapper employeeRequestMapper,
                               EmployeeResponseMapper employeeResponseMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeRequestMapper = employeeRequestMapper;
        this.employeeResponseMapper = employeeResponseMapper;
    }

    public List<EmployeeResponseModel> getEmployees(){
       List<Employee> employees  =employeeRepository.findAll();
       List<EmployeeResponseModel>  listResponseModels  = employeeResponseMapper.entityToResponseModelList(employees);
       return listResponseModels;
    }


    public EmployeeResponseModel getEmployeeByEmployeeId(String employeeId){
        Employee employee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId(employeeId);
        if (employee==null){
            throw   new NotFoundException("Employee with employeeId="+ employeeId + " not found.");
        }
        return employeeResponseMapper.entityToResponseModel(employee);
    }


    public EmployeeResponseModel addEmployee(EmployeeRequestModel postData){
        EmployeeIdentifier id = new EmployeeIdentifier();
        Address address = new Address(postData.getStreetAddress(),
                postData.getCity(),
                postData.getProvince(),
                postData.getCountry(),
                postData.getPostalCode()
        );
        Employee employee = employeeRequestMapper.requestModelToEntity(postData, address);
        Employee savedEmployee = null;

        savedEmployee = employeeRepository.save(employee);

        if (savedEmployee==null){
            throw new NotFoundException("new employee can't be saved");
        }

        return employeeResponseMapper.entityToResponseModel(savedEmployee);
    }


    public EmployeeResponseModel updateEmployee(String employeeId, EmployeeRequestModel postData){

        return null;
    }




}
