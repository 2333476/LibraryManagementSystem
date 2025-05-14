package com.example.loanservice.datamapperlayer;

import com.example.loanservice.datalayer.*;
import com.example.loanservice.presentationlayer.LoanRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LoanRequestMapper {

    @Mapping(target = "loanIdentifier", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "penaltyAmount", ignore = true)
    @Mapping(target = "bookIdentifier", source = "bookId")
    @Mapping(target = "customerIdentifier", source = "customerId")
    @Mapping(target = "employeeIdentifier", source = "employeeId")
    @Mapping(target = "loanStatus", expression = "java(com.example.loanservice.datalayer.LoanStatus.ACTIVE)")
    Loan requestModelToEntity(LoanRequestModel request);

    @Mapping(target = "loanIdentifier", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "penaltyAmount", ignore = true)
    @Mapping(target = "bookIdentifier", source = "bookId")
    @Mapping(target = "customerIdentifier", source = "customerId")
    @Mapping(target = "employeeIdentifier", source = "employeeId")
    @Mapping(target = "loanStatus", ignore = true)
    void updateEntityFromModel(LoanRequestModel request, @MappingTarget Loan loan);

    // Méthodes de conversion utilisées pour les identifiants personnalisés

    default BookIdentifier mapToBookIdentifier(String bookId) {
        return new BookIdentifier(bookId);
    }

    default CustomerIdentifier mapToCustomerIdentifier(String customerId) {
        return new CustomerIdentifier(customerId);
    }

    default EmployeeIdentifier mapToEmployeeIdentifier(String employeeId) {
        return new EmployeeIdentifier(employeeId);
    }
}
