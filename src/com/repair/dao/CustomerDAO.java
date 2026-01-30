package com.repair.dao;

import com.repair.bean.Customer;
import com.repair.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public Customer findCustomer(String customerID) throws SQLException {
        String sql = "SELECT * FROM CUSTOMER_TBL WHERE CUSTOMER_ID = ?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customerID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer c = new Customer();
                    c.setCustomerID(rs.getString("CUSTOMER_ID"));
                    c.setFullName(rs.getString("FULL_NAME"));
                    c.setAddressLine(rs.getString("ADDRESS_LINE"));
                    c.setCity(rs.getString("CITY"));
                    c.setPincode(rs.getString("PINCODE"));
                    c.setPrimaryPhone(rs.getString("PRIMARY_PHONE"));
                    c.setStatus(rs.getString("STATUS"));
                    return c;
                }
            }
        }
        return null;
    }

    public List<Customer> viewAllCustomers() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMER_TBL";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getString("CUSTOMER_ID"));
                c.setFullName(rs.getString("FULL_NAME"));
                c.setAddressLine(rs.getString("ADDRESS_LINE"));
                c.setCity(rs.getString("CITY"));
                c.setPincode(rs.getString("PINCODE"));
                c.setPrimaryPhone(rs.getString("PRIMARY_PHONE"));
                c.setStatus(rs.getString("STATUS"));
                list.add(c);
            }
        }
        return list;
    }

    public boolean insertCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO CUSTOMER_TBL " +
                "(CUSTOMER_ID,FULL_NAME,ADDRESS_LINE,CITY,PINCODE,PRIMARY_PHONE,STATUS) " +
                "VALUES (?,?,?,?,?,?,?)";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customer.getCustomerID());
            ps.setString(2, customer.getFullName());
            ps.setString(3, customer.getAddressLine());
            ps.setString(4, customer.getCity());
            ps.setString(5, customer.getPincode());
            ps.setString(6, customer.getPrimaryPhone());
            ps.setString(7, customer.getStatus());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean updateCustomerStatus(String customerID, String status) throws SQLException {
        String sql = "UPDATE CUSTOMER_TBL SET STATUS = ? WHERE CUSTOMER_ID = ?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, customerID);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean deleteCustomer(String customerID) throws SQLException {
        String sql = "DELETE FROM CUSTOMER_TBL WHERE CUSTOMER_ID = ?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customerID);
            return ps.executeUpdate() == 1;
        }
    }
}
