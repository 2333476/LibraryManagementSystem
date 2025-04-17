package com.example.customersservice.presentationlayer;


import com.example.customersservice.dataaccesslayer.PhoneNumber;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerRequestModel {
    private String lastName;
    private String firstName;
    private String emailAddress;
    private String streetAddress;
    private String postalCode;
    private String city;
    private String province;
    private String  username;
    private String  password1;
    private String  password2;

    List<PhoneNumber> phoneNumbers;
}
