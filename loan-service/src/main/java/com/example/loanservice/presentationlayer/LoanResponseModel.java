package com.example.loanservice.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(toBuilder = true) // Ajout de toBuilder = true
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoanResponseModel {

    // Identifiants
    String loanId;
    String bookId;
    String customerId;
    String employeeId;

    // Dates clés
    LocalDate loanDate;
    LocalDate returnDate;

    // Drapeaux logiques
    boolean isOverdue;

    // Données agrégées
    String bookTitle;
    String bookAuthor;
    String customerFullName;
    String employeeFullName;
}
