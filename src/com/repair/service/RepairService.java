package com.repair.service;

import com.repair.bean.Customer;
import com.repair.bean.ServiceTicket;
import com.repair.bean.Technician;
import com.repair.dao.CustomerDAO;
import com.repair.dao.ServiceTicketDAO;
import com.repair.dao.TechnicianDAO;
import com.repair.util.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class RepairService {

    private CustomerDAO customerDAO;
    private TechnicianDAO technicianDAO;
    private ServiceTicketDAO ticketDAO;

    public RepairService() {
        customerDAO = new CustomerDAO();
        technicianDAO = new TechnicianDAO();
        ticketDAO = new ServiceTicketDAO();
    }

    public Customer viewCustomerDetails(String customerID) throws SQLException, ValidationException {
        if (customerID == null || customerID.trim().isEmpty()) {
            throw new ValidationException("Customer ID cannot be blank");
        }
        return customerDAO.findCustomer(customerID);
    }

    public List<Customer> viewAllCustomers() throws SQLException {
        return customerDAO.viewAllCustomers();
    }

    public boolean registerNewCustomer(Customer customer) throws SQLException, ValidationException {
        if (customer == null ||
                customer.getCustomerID() == null || customer.getCustomerID().trim().isEmpty() ||
                customer.getFullName() == null || customer.getFullName().trim().isEmpty() ||
                customer.getAddressLine() == null || customer.getAddressLine().trim().isEmpty() ||
                customer.getCity() == null || customer.getCity().trim().isEmpty() ||
                customer.getPincode() == null || customer.getPincode().trim().isEmpty() ||
                customer.getPrimaryPhone() == null || customer.getPrimaryPhone().trim().isEmpty()) {
            throw new ValidationException("All mandatory customer fields must be provided");
        }

        if (customerDAO.findCustomer(customer.getCustomerID()) != null) {
            throw new ValidationException("Customer ID already exists");
        }

        customer.setStatus("ACTIVE");
        return customerDAO.insertCustomer(customer);
    }

    public Technician viewTechnicianDetails(String technicianID) throws SQLException, ValidationException {
        if (technicianID == null || technicianID.trim().isEmpty()) {
            throw new ValidationException("Technician ID cannot be blank");
        }
        return technicianDAO.findTechnician(technicianID);
    }

    public List<Technician> viewAllTechnicians() throws SQLException {
        return technicianDAO.viewAllTechnicians();
    }

    public boolean registerNewTechnician(Technician technician) throws SQLException, ValidationException {
        if (technician == null ||
                technician.getTechnicianID() == null || technician.getTechnicianID().trim().isEmpty() ||
                technician.getFullName() == null || technician.getFullName().trim().isEmpty() ||
                technician.getSkillCategory() == null || technician.getSkillCategory().trim().isEmpty() ||
                technician.getServiceRegion() == null || technician.getServiceRegion().trim().isEmpty() ||
                technician.getPhone() == null || technician.getPhone().trim().isEmpty()) {
            throw new ValidationException("All mandatory technician fields must be provided");
        }

        if (technicianDAO.findTechnician(technician.getTechnicianID()) != null) {
            throw new ValidationException("Technician ID already exists");
        }

        technician.setStatus("ACTIVE");
        return technicianDAO.insertTechnician(technician);
    }

    public boolean createServiceTicket(String customerID,
                                       String applianceType,
                                       String problemDescription,
                                       Date ticketDate)
            throws SQLException, ValidationException {

        if (customerID == null || customerID.trim().isEmpty() ||
                applianceType == null || applianceType.trim().isEmpty() ||
                problemDescription == null || problemDescription.trim().isEmpty() ||
                ticketDate == null) {
            throw new ValidationException("Ticket fields cannot be blank");
        }

        Customer c = customerDAO.findCustomer(customerID);
        if (c == null || !"ACTIVE".equalsIgnoreCase(c.getStatus())) {
            return false;
        }

        Connection con = null;
        try {
            con = DBUtil.getDBConnection();
            con.setAutoCommit(false);

            int newID = ticketDAO.generateTicketID();
            ServiceTicket ticket = new ServiceTicket();
            ticket.setTicketID(newID);
            ticket.setCustomerID(customerID);
            ticket.setTechnicianID(null);
            ticket.setApplianceType(applianceType);
            ticket.setProblemDescription(problemDescription);
            ticket.setTicketDate(ticketDate);
            ticket.setScheduledVisitDate(null);
            ticket.setScheduledVisitSlot(null);
            ticket.setVisitOutcome(null);
            ticket.setServiceCharge(null);
            ticket.setStatus("OPEN");

            boolean ok = ticketDAO.insertTicket(ticket);
            if (ok) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    public boolean assignTechnicianAndScheduleVisit(int ticketID,
                                                    String technicianID,
                                                    Date visitDate,
                                                    String visitSlot)
            throws SQLException, ValidationException, TechnicianNotSuitableException {

        if (ticketID <= 0 ||
                technicianID == null || technicianID.trim().isEmpty() ||
                visitDate == null ||
                visitSlot == null || visitSlot.trim().isEmpty()) {
            throw new ValidationException("Assignment fields cannot be blank");
        }

        ServiceTicket ticket = ticketDAO.findTicket(ticketID);
        if (ticket == null) {
            return false;
        }
        if ("CLOSED".equalsIgnoreCase(ticket.getStatus()) ||
                "CANCELLED".equalsIgnoreCase(ticket.getStatus())) {
            return false;
        }

        Technician tech = technicianDAO.findTechnician(technicianID);
        if (tech == null || !"ACTIVE".equalsIgnoreCase(tech.getStatus())) {
            return false;
        }

        // Simple skill/region check placeholder
        if (!"Multi Appliance".equalsIgnoreCase(tech.getSkillCategory()) &&
                !ticket.getApplianceType().toLowerCase().contains(
                        tech.getSkillCategory().toLowerCase().split(" ")[0])) {
            throw new TechnicianNotSuitableException("Technician skill does not match appliance type");
        }

        Connection con = null;
        try {
            con = DBUtil.getDBConnection();
            con.setAutoCommit(false);

            boolean ok = ticketDAO.updateAssignmentAndSchedule(ticketID, technicianID, visitDate, visitSlot, "SCHEDULED");
            if (ok) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    public boolean updateVisitOutcomeAndCloseTicket(int ticketID,
                                                    String visitOutcome,
                                                    BigDecimal serviceCharge,
                                                    String finalStatus)
            throws SQLException, ValidationException {

        if (ticketID <= 0 ||
                visitOutcome == null || visitOutcome.trim().isEmpty() ||
                serviceCharge == null || serviceCharge.compareTo(BigDecimal.ZERO) < 0 ||
                finalStatus == null || finalStatus.trim().isEmpty()) {
            throw new ValidationException("Visit outcome fields invalid");
        }

        if (!"CLOSED".equalsIgnoreCase(finalStatus) &&
                !"WAITING_PART".equalsIgnoreCase(finalStatus)) {
            throw new ValidationException("Final status must be CLOSED or WAITING_PART");
        }

        ServiceTicket ticket = ticketDAO.findTicket(ticketID);
        if (ticket == null) {
            return false;
        }
        if ("CLOSED".equalsIgnoreCase(ticket.getStatus()) ||
                "CANCELLED".equalsIgnoreCase(ticket.getStatus())) {
            return false;
        }

        Connection con = null;
        try {
            con = DBUtil.getDBConnection();
            con.setAutoCommit(false);

            boolean ok = ticketDAO.updateVisitOutcomeAndCharge(ticketID, visitOutcome, serviceCharge, finalStatus);
            if (ok) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    public List<ServiceTicket> listTicketsByCustomer(String customerID) throws SQLException {
        return ticketDAO.findTicketsByCustomer(customerID);
    }

    public List<ServiceTicket> listTicketsByTechnician(String technicianID) throws SQLException {
        return ticketDAO.findTicketsByTechnician(technicianID);
    }

    public boolean removeCustomer(String customerID)
            throws SQLException, ValidationException, ActiveTicketsExistException {

        if (customerID == null || customerID.trim().isEmpty()) {
            throw new ValidationException("Customer ID cannot be blank");
        }

        List<ServiceTicket> active = ticketDAO.findActiveTicketsForCustomer(customerID);
        if (!active.isEmpty()) {
            throw new ActiveTicketsExistException("Customer has active tickets, cannot delete");
        }

        return customerDAO.deleteCustomer(customerID);
    }

    public boolean removeTechnician(String technicianID)
            throws SQLException, ValidationException, ActiveTicketsExistException {

        if (technicianID == null || technicianID.trim().isEmpty()) {
            throw new ValidationException("Technician ID cannot be blank");
        }

        List<ServiceTicket> active = ticketDAO.findActiveTicketsForTechnician(technicianID);
        if (!active.isEmpty()) {
            throw new ActiveTicketsExistException("Technician has active tickets, cannot delete");
        }

        return technicianDAO.deleteTechnician(technicianID);
    }
}
