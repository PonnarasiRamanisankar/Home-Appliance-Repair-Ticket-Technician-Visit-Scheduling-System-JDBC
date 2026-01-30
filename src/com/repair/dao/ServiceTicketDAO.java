package com.repair.dao;

import com.repair.bean.ServiceTicket;
import com.repair.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceTicketDAO {

    public int generateTicketID() throws SQLException {
        String sql = "SELECT NVL(MAX(TICKET_ID),0) + 1 AS NEXT_ID FROM SERVICE_TICKET_TBL";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("NEXT_ID");
            }
        }
        return 1;
    }

    public boolean insertTicket(ServiceTicket ticket) throws SQLException {
        String sql = "INSERT INTO SERVICE_TICKET_TBL " +
                "(TICKET_ID,CUSTOMER_ID,TECHNICIAN_ID,APPLIANCE_TYPE," +
                "PROBLEM_DESCRIPTION,TICKET_DATE,SCHEDULED_VISIT_DATE," +
                "SCHEDULED_VISIT_SLOT,VISIT_OUTCOME,SERVICE_CHARGE,STATUS) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ticket.getTicketID());
            ps.setString(2, ticket.getCustomerID());
            ps.setString(3, ticket.getTechnicianID());
            ps.setString(4, ticket.getApplianceType());
            ps.setString(5, ticket.getProblemDescription());
            ps.setDate(6, ticket.getTicketDate());
            ps.setDate(7, ticket.getScheduledVisitDate());
            ps.setString(8, ticket.getScheduledVisitSlot());
            ps.setString(9, ticket.getVisitOutcome());
            if (ticket.getServiceCharge() != null) {
                ps.setBigDecimal(10, ticket.getServiceCharge());
            } else {
                ps.setNull(10, Types.NUMERIC);
            }
            ps.setString(11, ticket.getStatus());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean updateAssignmentAndSchedule(int ticketID,
                                               String technicianID,
                                               Date visitDate,
                                               String visitSlot,
                                               String newStatus) throws SQLException {
        String sql = "UPDATE SERVICE_TICKET_TBL " +
                "SET TECHNICIAN_ID = ?, SCHEDULED_VISIT_DATE = ?, " +
                "SCHEDULED_VISIT_SLOT = ?, STATUS = ? WHERE TICKET_ID = ?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, technicianID);
            ps.setDate(2, visitDate);
            ps.setString(3, visitSlot);
            ps.setString(4, newStatus);
            ps.setInt(5, ticketID);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean updateVisitOutcomeAndCharge(int ticketID,
                                               String visitOutcome,
                                               BigDecimal serviceCharge,
                                               String newStatus) throws SQLException {
        String sql = "UPDATE SERVICE_TICKET_TBL " +
                "SET VISIT_OUTCOME = ?, SERVICE_CHARGE = ?, STATUS = ? " +
                "WHERE TICKET_ID = ?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, visitOutcome);
            ps.setBigDecimal(2, serviceCharge);
            ps.setString(3, newStatus);
            ps.setInt(4, ticketID);
            return ps.executeUpdate() == 1;
        }
    }

    public ServiceTicket findTicket(int ticketID) throws SQLException {
        String sql = "SELECT * FROM SERVICE_TICKET_TBL WHERE TICKET_ID = ?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, ticketID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ServiceTicket t = new ServiceTicket();
                    t.setTicketID(rs.getInt("TICKET_ID"));
                    t.setCustomerID(rs.getString("CUSTOMER_ID"));
                    t.setTechnicianID(rs.getString("TECHNICIAN_ID"));
                    t.setApplianceType(rs.getString("APPLIANCE_TYPE"));
                    t.setProblemDescription(rs.getString("PROBLEM_DESCRIPTION"));
                    t.setTicketDate(rs.getDate("TICKET_DATE"));
                    t.setScheduledVisitDate(rs.getDate("SCHEDULED_VISIT_DATE"));
                    t.setScheduledVisitSlot(rs.getString("SCHEDULED_VISIT_SLOT"));
                    t.setVisitOutcome(rs.getString("VISIT_OUTCOME"));
                    t.setServiceCharge(rs.getBigDecimal("SERVICE_CHARGE"));
                    t.setStatus(rs.getString("STATUS"));
                    return t;
                }
            }
        }
        return null;
    }

    public List<ServiceTicket> findTicketsByCustomer(String customerID) throws SQLException {
        List<ServiceTicket> list = new ArrayList<>();
        String sql = "SELECT * FROM SERVICE_TICKET_TBL WHERE CUSTOMER_ID = ?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customerID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ServiceTicket t = new ServiceTicket();
                    t.setTicketID(rs.getInt("TICKET_ID"));
                    t.setCustomerID(rs.getString("CUSTOMER_ID"));
                    t.setTechnicianID(rs.getString("TECHNICIAN_ID"));
                    t.setApplianceType(rs.getString("APPLIANCE_TYPE"));
                    t.setProblemDescription(rs.getString("PROBLEM_DESCRIPTION"));
                    t.setTicketDate(rs.getDate("TICKET_DATE"));
                    t.setScheduledVisitDate(rs.getDate("SCHEDULED_VISIT_DATE"));
                    t.setScheduledVisitSlot(rs.getString("SCHEDULED_VISIT_SLOT"));
                    t.setVisitOutcome(rs.getString("VISIT_OUTCOME"));
                    t.setServiceCharge(rs.getBigDecimal("SERVICE_CHARGE"));
                    t.setStatus(rs.getString("STATUS"));
                    list.add(t);
                }
            }
        }
        return list;
    }

    public List<ServiceTicket> findTicketsByTechnician(String technicianID) throws SQLException {
        List<ServiceTicket> list = new ArrayList<>();
        String sql = "SELECT * FROM SERVICE_TICKET_TBL WHERE TECHNICIAN_ID = ?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, technicianID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ServiceTicket t = new ServiceTicket();
                    t.setTicketID(rs.getInt("TICKET_ID"));
                    t.setCustomerID(rs.getString("CUSTOMER_ID"));
                    t.setTechnicianID(rs.getString("TECHNICIAN_ID"));
                    t.setApplianceType(rs.getString("APPLIANCE_TYPE"));
                    t.setProblemDescription(rs.getString("PROBLEM_DESCRIPTION"));
                    t.setTicketDate(rs.getDate("TICKET_DATE"));
                    t.setScheduledVisitDate(rs.getDate("SCHEDULED_VISIT_DATE"));
                    t.setScheduledVisitSlot(rs.getString("SCHEDULED_VISIT_SLOT"));
                    t.setVisitOutcome(rs.getString("VISIT_OUTCOME"));
                    t.setServiceCharge(rs.getBigDecimal("SERVICE_CHARGE"));
                    t.setStatus(rs.getString("STATUS"));
                    list.add(t);
                }
            }
        }
        return list;
    }

    public List<ServiceTicket> findActiveTicketsForCustomer(String customerID) throws SQLException {
        List<ServiceTicket> list = new ArrayList<>();
        String sql = "SELECT * FROM SERVICE_TICKET_TBL " +
                "WHERE CUSTOMER_ID = ? " +
                "AND STATUS IN ('OPEN','ASSIGNED','SCHEDULED','IN_PROGRESS','WAITING_PART')";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customerID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ServiceTicket t = new ServiceTicket();
                    t.setTicketID(rs.getInt("TICKET_ID"));
                    t.setCustomerID(rs.getString("CUSTOMER_ID"));
                    t.setTechnicianID(rs.getString("TECHNICIAN_ID"));
                    t.setStatus(rs.getString("STATUS"));
                    list.add(t);
                }
            }
        }
        return list;
    }

    public List<ServiceTicket> findActiveTicketsForTechnician(String technicianID) throws SQLException {
        List<ServiceTicket> list = new ArrayList<>();
        String sql = "SELECT * FROM SERVICE_TICKET_TBL " +
                "WHERE TECHNICIAN_ID = ? " +
                "AND STATUS IN ('OPEN','ASSIGNED','SCHEDULED','IN_PROGRESS','WAITING_PART')";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, technicianID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ServiceTicket t = new ServiceTicket();
                    t.setTicketID(rs.getInt("TICKET_ID"));
                    t.setCustomerID(rs.getString("CUSTOMER_ID"));
                    t.setTechnicianID(rs.getString("TECHNICIAN_ID"));
                    t.setStatus(rs.getString("STATUS"));
                    list.add(t);
                }
            }
        }
        return list;
    }
}
