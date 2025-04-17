package com.example.employeeservice.datalayer;


import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Embeddable
@EqualsAndHashCode
@Getter
public class Address {
    private String streetAddress;
    private String city;
    private String province;
    private String country;
    private String postalCode;

    public Address() {
    }

    public Address(String streetAddress, String city,
                   String province,
                   String country,
                   String postalCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
    }
}
