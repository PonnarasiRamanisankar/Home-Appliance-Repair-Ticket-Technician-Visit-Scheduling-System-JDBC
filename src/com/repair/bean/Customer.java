package com.repair.bean;

public class Customer {

    private String customerID;
    private String fullName;
    private String addressLine;
    private String city;
    private String pincode;
    private String primaryPhone;
    private String status;

    public String getCustomerID() {
        return customerID;
    }
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getAddressLine() {
        return addressLine;
    }
    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getPincode() {
        return pincode;
    }
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
    public String getPrimaryPhone() {
        return primaryPhone;
    }
    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
