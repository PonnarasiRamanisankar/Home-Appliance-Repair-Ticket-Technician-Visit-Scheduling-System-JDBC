package com.repair.bean;

public class Technician {

    private String technicianID;
    private String fullName;
    private String skillCategory;
    private String serviceRegion;
    private String phone;
    private String status;

    public String getTechnicianID() {
        return technicianID;
    }
    public void setTechnicianID(String technicianID) {
        this.technicianID = technicianID;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getSkillCategory() {
        return skillCategory;
    }
    public void setSkillCategory(String skillCategory) {
        this.skillCategory = skillCategory;
    }
    public String getServiceRegion() {
        return serviceRegion;
    }
    public void setServiceRegion(String serviceRegion) {
        this.serviceRegion = serviceRegion;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
