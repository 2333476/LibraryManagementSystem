package com.example.apigatewayservice.presentationlayer.employeedto;

public class EmployeeResponseModel {

    private final String employeeId;
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final String phoneNumber;
    private final Double salary;
    private final Double commissionRate;
    private final Department department;
    private final String streetAddress;
    private final String city;
    private final String province;
    private final String country;
    private final String postalCode;

    // ✅ Constructeur privé
    private EmployeeResponseModel(String employeeId, String firstName, String lastName, String emailAddress,
                                  String phoneNumber, Double salary, Double commissionRate, Department department,
                                  String streetAddress, String city, String province, String country, String postalCode) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.commissionRate = commissionRate;
        this.department = department;
        this.streetAddress = streetAddress;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
    }

    // ✅ Builder
    public static class Builder {
        private String employeeId;
        private String firstName;
        private String lastName;
        private String emailAddress;
        private String phoneNumber;
        private Double salary;
        private Double commissionRate;
        private Department department;
        private String streetAddress;
        private String city;
        private String province;
        private String country;
        private String postalCode;

        public Builder employeeId(String employeeId) {
            this.employeeId = employeeId;
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

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder salary(Double salary) {
            this.salary = salary;
            return this;
        }

        public Builder commissionRate(Double commissionRate) {
            this.commissionRate = commissionRate;
            return this;
        }

        public Builder department(Department department) {
            this.department = department;
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

        public EmployeeResponseModel build() {
            return new EmployeeResponseModel(employeeId, firstName, lastName, emailAddress,
                    phoneNumber, salary, commissionRate, department,
                    streetAddress, city, province, country, postalCode);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    // ✅ Getters
    public String getEmployeeId() { return employeeId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmailAddress() { return emailAddress; }
    public String getPhoneNumber() { return phoneNumber; }
    public Double getSalary() { return salary; }
    public Double getCommissionRate() { return commissionRate; }
    public Department getDepartment() { return department; }
    public String getStreetAddress() { return streetAddress; }
    public String getCity() { return city; }
    public String getProvince() { return province; }
    public String getCountry() { return country; }
    public String getPostalCode() { return postalCode; }
}
