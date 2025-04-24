package com.example.apigatewayservice.presentationlayer.customerdto;

import jakarta.persistence.Embeddable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Embeddable
public class Address {

    private String streetAddress;
    private String city;
    private String province;
    private String country;
    private String postalCode;

    public Address() {
        // Required by JPA
    }

    public Address(@NotNull String streetAddress, @NotNull String city, @NotNull String province,
                   @NotNull String country, @NotNull String postalCode) {

        this.streetAddress = Objects.requireNonNull(streetAddress);
        this.city = Objects.requireNonNull(city);
        this.province = Objects.requireNonNull(province);
        this.country = Objects.requireNonNull(country);
        this.postalCode = Objects.requireNonNull(postalCode);
    }

    public @NotNull String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public @NotNull String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public @NotNull String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public @NotNull String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public @NotNull String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return streetAddress.equals(address.streetAddress)
                && city.equals(address.city)
                && province.equals(address.province)
                && country.equals(address.country)
                && postalCode.equals(address.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetAddress, city, province, country, postalCode);
    }
}
