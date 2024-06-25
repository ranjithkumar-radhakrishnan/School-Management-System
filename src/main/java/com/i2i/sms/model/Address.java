package com.i2i.sms.model;

import jakarta.persistence.*;

/**
* This class represents a address of the student which contains information such as address Id, doorNo, street, city, state, pincode.
*/
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int addressId;
    private String doorNo;
    private String street;
    private String city;
    private String state;
    private int pincode;

    @OneToOne(mappedBy = "address")
    private Student student;

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
    public int getAddressId() {
        return addressId;
    }
    
    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }
    public String getDoorNo() {
        return doorNo;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    public String getStreet() {
        return street;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getCity() {
        return city;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
    public int getPincode() {
        return pincode;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    public Student getStudent() {
        return student;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n\t\tDoor No: ").append(doorNo)
                     .append(" Street: ").append(street)
                     .append(" City: ").append(city)
                     .append(" State: ").append(state)
                     .append(" pincode: ").append(pincode);
        return stringBuilder.toString();
    }
} 