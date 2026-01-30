package com.repair.bean;

 
import java.math.BigDecimal;
import java.sql.Date;

public class ServiceTicket {

    private int ticketID;
    private String customerID;
    private String technicianID;
    private String applianceType;
    private String problemDescription;
    private Date ticketDate;
    private Date scheduledVisitDate;
    private String scheduledVisitSlot;
    private String visitOutcome;
    private BigDecimal serviceCharge;
    private String status;

    public int getTicketID() {
        return ticketID;
    }
    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }
    public String getCustomerID() {
        return customerID;
    }
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    public String getTechnicianID() {
        return technicianID;
    }
    public void setTechnicianID(String technicianID) {
        this.technicianID = technicianID;
    }
    public String getApplianceType() {
        return applianceType;
    }
    public void setApplianceType(String applianceType) {
        this.applianceType = applianceType;
    }
    public String getProblemDescription() {
        return problemDescription;
    }
    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }
    public Date getTicketDate() {
        return ticketDate;
    }
    public void setTicketDate(Date ticketDate) {
        this.ticketDate = ticketDate;
    }
    public Date getScheduledVisitDate() {
        return scheduledVisitDate;
    }
    public void setScheduledVisitDate(Date scheduledVisitDate) {
        this.scheduledVisitDate = scheduledVisitDate;
    }
    public String getScheduledVisitSlot() {
        return scheduledVisitSlot;
    }
    public void setScheduledVisitSlot(String scheduledVisitSlot) {
        this.scheduledVisitSlot = scheduledVisitSlot;
    }
    public String getVisitOutcome() {
        return visitOutcome;
    }
    public void setVisitOutcome(String visitOutcome) {
        this.visitOutcome = visitOutcome;
    }
    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }
    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
