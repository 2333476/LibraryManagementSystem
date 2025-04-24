package com.example.employeeservice.businesslayer;



import com.example.employeeservice.datalayer.Address;
import com.example.employeeservice.datalayer.Employee;
import com.example.employeeservice.datalayer.EmployeeIdentifier;
import com.example.employeeservice.datalayer.EmployeeRepository;
import com.example.employeeservice.datamapperlayer.EmployeeRequestMapper;
import com.example.employeeservice.datamapperlayer.EmployeeResponseMapper;
import com.example.employeeservice.presentationlayer.EmployeeRequestModel;
import com.example.employeeservice.presentationlayer.EmployeeResponseModel;
import com.example.employeeservice.utils.exceptions.DuplicateEmailException;
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


    @Override
    public EmployeeResponseModel addEmployee(EmployeeRequestModel postData) {

        if (employeeRepository.existsByEmailAddress(postData.getEmailAddress())) {
            throw new DuplicateEmailException("Employee with email " + postData.getEmailAddress() + " already exists.");
        }

        Address address = new Address(
                postData.getStreetAddress(),
                postData.getCity(),
                postData.getProvince(),
                postData.getCountry(),
                postData.getPostalCode()
        );

        Employee employee = employeeRequestMapper.requestModelToEntity(postData, address);

        Employee savedEmployee = employeeRepository.save(employee);

        return employeeResponseMapper.entityToResponseModel(savedEmployee);
    }



    @Override
    public EmployeeResponseModel updateEmployee(String employeeId, EmployeeRequestModel postData) {

        Employee existingEmployee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId(employeeId);

        if (existingEmployee == null) {
            throw new NotFoundException("Employee with employeeId=" + employeeId + " not found.");
        }


        Employee employeeWithSameEmail = employeeRepository.findEmployeeByEmailAddress(postData.getEmailAddress());

        if (employeeWithSameEmail != null &&
                !employeeWithSameEmail.getEmployeeIdentifier().getEmployeeId().equals(employeeId)) {
            throw new DuplicateEmailException("The email address " + postData.getEmailAddress() + " is already in use.");
        }


        Address updatedAddress = new Address(
                postData.getStreetAddress(),
                postData.getCity(),
                postData.getProvince(),
                postData.getCountry(),
                postData.getPostalCode()
        );
        existingEmployee.setAddress(updatedAddress);


        existingEmployee.setFirstName(postData.getFirstName());
        existingEmployee.setLastName(postData.getLastName());
        existingEmployee.setEmailAddress(postData.getEmailAddress());
        existingEmployee.setPhoneNumber(postData.getPhoneNumber());
        existingEmployee.setSalary(postData.getSalary());
        existingEmployee.setCommissionRate(postData.getCommissionRate());
        existingEmployee.setDepartment(postData.getDepartment());


        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return employeeResponseMapper.entityToResponseModel(updatedEmployee);
    }




    @Override
    public void deleteEmployee(String employeeId) {
        Employee existingEmployee = employeeRepository.findEmployeeByEmployeeIdentifier_EmployeeId(employeeId);

        if (existingEmployee == null) {
            throw new NotFoundException("Employee with employeeId=" + employeeId + " not found.");
        }

        employeeRepository.delete(existingEmployee);
    }








}
