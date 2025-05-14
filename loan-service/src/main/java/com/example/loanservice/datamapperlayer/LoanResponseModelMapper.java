package com.example.loanservice.datamapperlayer;

import com.example.loanservice.datalayer.Loan;
import com.example.loanservice.presentationlayer.LoanResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanResponseModelMapper {

    @Mapping(target = "loanId", expression = "java(loan.getLoanIdentifier().getLoanId())")
    @Mapping(target = "bookId", expression = "java(loan.getBookIdentifier().getBookId())")
    @Mapping(target = "customerId", expression = "java(loan.getCustomerIdentifier().getCustomerId())")
    @Mapping(target = "employeeId", expression = "java(loan.getEmployeeIdentifier().getEmployeeId())")
    @Mapping(target = "isOverdue", expression = "java(loan.getReturnDate().isBefore(java.time.LocalDate.now()))")

    // Champs enrichis ignorés ici : ajoutés à la main dans le service
    @Mapping(target = "bookTitle", ignore = true)
    @Mapping(target = "bookAuthor", ignore = true)
    @Mapping(target = "customerFullName", ignore = true)
    @Mapping(target = "employeeFullName", ignore = true)
    LoanResponseModel entityToResponseModel(Loan loan);

    List<LoanResponseModel> entityToResponseModelList(List<Loan> loans);
}
