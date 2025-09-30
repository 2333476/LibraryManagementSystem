package com.example.loanservice.datamapperlayer.customerdto;

import com.example.loanservice.datamapperlayer.customerdto.PhoneNumber;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerResponseModel   {
    private String customerId; //public identifier
    private String lastName;
    private String firstName;
    private String emailAddress;

    private String streetAddress;
    private String postalCode;
    private String city;
    private String province;
    private List<PhoneNumber> phoneNumbers;
//    private String phoneType;
//    private String phoneNumber;
}
