package com.tqs.packagepal.model;

import java.util.Objects;

import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "packages")
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "package_id")
    private String packageId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeliveryStatus status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pickup_point", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PickupPoint pickupPoint;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "store", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Store store;

    // Constructors

    public Package() {
    }

    public Package(String packageId, String userName, String userEmail, DeliveryStatus status,
            PickupPoint pickupPoint, Store store) {
        this.packageId = packageId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.status = status;
        this.pickupPoint = pickupPoint;
        this.store = store;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public String getPackageId() {
        return packageId;
    }

    public PickupPoint getPickupPoint() {
        return pickupPoint;
    }

    public Store getStore() {
        return store;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public void setPickupPoint(PickupPoint pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    // Equals e Hashcode

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Package)) {
            return false;
        }
        Package pack = (Package) o;
        return Objects.equals(id, pack.id) && Objects.equals(packageId, pack.packageId)
                && Objects.equals(pickupPoint, pack.pickupPoint) && Objects.equals(store, pack.store)
                && Objects.equals(userEmail, pack.userEmail) && Objects.equals(userName, pack.userName)
                && Objects.equals(status, pack.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, packageId, pickupPoint, store, userEmail, userName, status);
    }

    // toString

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", packageId='" + getPackageId() + "'" + ", pickupPoint='"
                + getPickupPoint() + "'" + ", store='" + getStore() + "'" + ", userEmail='" + getUserEmail() + "'"
                + ", userName='" + getUserName() + "'" + ", status='" + getStatus() + "'" + "}";
    }

}   