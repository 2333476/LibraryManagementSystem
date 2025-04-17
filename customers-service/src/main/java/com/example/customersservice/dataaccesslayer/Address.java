package com.example.customersservice.dataaccesslayer;

import jakarta.persistence.Embeddable;
import lombok.*;


@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Address {
    private String streetAddress;

    private String city;
    private String province;
    private String postalCode;

}
