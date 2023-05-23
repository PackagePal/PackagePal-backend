package com.tqs.packagepal.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pickup_point")
public class PickupPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "postalcode")
    private String postalCode;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    // Construtores

    public PickupPoint() {
    }

    public PickupPoint(String name, String address, String city, String postalCode, Double lat, Double lng) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.lat = lat;
        this.lng = lng;
    }

    // Getters

    public String getAddress() {
        return address;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    // Setters

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;

    }

    public void setCity(String city) {
        this.city = city;

    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;

    }

    public void setLat(Double lat) {
        this.lat = lat;

    }

    public void setLng(Double lng) {
        this.lng = lng;

    }

    // Equals e Hashcode

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PickupPoint)) {
            return false;
        }
        PickupPoint pickupPoint = (PickupPoint) o;
        return Objects.equals(id, pickupPoint.id) && Objects.equals(name, pickupPoint.name)
                && Objects.equals(address, pickupPoint.address) && Objects.equals(city, pickupPoint.city)
                && Objects.equals(postalCode, pickupPoint.postalCode) && Objects.equals(lat, pickupPoint.lat)
                && Objects.equals(lng, pickupPoint.lng);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, city, postalCode, lat, lng);
    }

    // toString

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", address='" + getAddress() + "'"
                + ", city='" + getCity() + "'" + ", postalCode='" + getPostalCode() + "'" + ", lat='" + getLat() + "'"
                + ", lng='" + getLng() + "'" + "}";
    }

}
