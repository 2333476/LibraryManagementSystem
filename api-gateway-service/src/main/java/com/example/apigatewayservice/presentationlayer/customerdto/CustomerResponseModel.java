package com.example.apigatewayservice.presentationlayer.customerdto;

import java.util.List;

public class CustomerResponseModel {

    private String customerId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String streetAddress;
    private String city;
    private String province;
    private String country;
    private String postalCode;
    private List<PhoneNumber> phoneNumbers;

    // ✅ Constructeur vide (obligatoire pour Jackson)
    public CustomerResponseModel() {}

    // ✅ Constructeur privé utilisé par le builder
    private CustomerResponseModel(String customerId, String firstName, String lastName, String emailAddress,
                                  String streetAddress, String city, String province, String country,
                                  String postalCode, List<PhoneNumber> phoneNumbers) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
        this.phoneNumbers = phoneNumbers;
    }

    // ✅ Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String customerId;
        private String firstName;
        private String lastName;
        private String emailAddress;
        private String streetAddress;
        private String city;
        private String province;
        private String country;
        private String postalCode;
        private List<PhoneNumber> phoneNumbers;

        public Builder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public Builder streetAddress(String streetAddress) {
            this.streetAddress = streetAddress;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder province(String province) {
            this.province = province;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder phoneNumbers(List<PhoneNumber> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
            return this;
        }

        public CustomerResponseModel build() {
            return new CustomerResponseModel(
                    customerId, firstName, lastName, emailAddress,
                    streetAddress, city, province, country,
                    postalCode, phoneNumbers
            );
        }
    }

    // ✅ Getters
    public String getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }


}
