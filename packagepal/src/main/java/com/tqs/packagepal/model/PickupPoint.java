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

    @Column(name = "email")
    private String email;

    // Construtores

    public PickupPoint() {
    }

    public PickupPoint(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }

    // Getters

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setters

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;

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
                && Objects.equals(address, pickupPoint.address) && Objects.equals(email, pickupPoint.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, email);
    }

    // toString

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", address='" + getAddress() + "'"
                + ", email='" + getEmail() + "'" + "}";
    }

}
