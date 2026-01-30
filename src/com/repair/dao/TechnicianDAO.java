package com.repair.dao;

import com.repair.bean.Technician;
import com.repair.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TechnicianDAO {

    public Technician findTechnician(String technicianID) throws SQLException {
        String sql = "SELECT * FROM TECHNICIAN_TBL WHERE TECHNICIAN_ID = ?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, technicianID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Technician t = new Technician();
                    t.setTechnicianID(rs.getString("TECHNICIAN_ID"));
                    t.setFullName(rs.getString("FULL_NAME"));
                    t.setSkillCategory(rs.getString("SKILL_CATEGORY"));
                    t.setServiceRegion(rs.getString("SERVICE_REGION"));
                    t.setPhone(rs.getString("PHONE"));
                    t.setStatus(rs.getString("STATUS"));
                    return t;
                }
            }
        }
        return null;
    }

    public List<Technician> viewAllTechnicians() throws SQLException {
        List<Technician> list = new ArrayList<>();
        String sql = "SELECT * FROM TECHNICIAN_TBL";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Technician t = new Technician();
                t.setTechnicianID(rs.getString("TECHNICIAN_ID"));
                t.setFullName(rs.getString("FULL_NAME"));
                t.setSkillCategory(rs.getString("SKILL_CATEGORY"));
                t.setServiceRegion(rs.getString("SERVICE_REGION"));
                t.setPhone(rs.getString("PHONE"));
                t.setStatus(rs.getString("STATUS"));
                list.add(t);
            }
        }
        return list;
    }

    public boolean insertTechnician(Technician technician) throws SQLException {
        String sql = "INSERT INTO TECHNICIAN_TBL " +
                "(TECHNICIAN_ID,FULL_NAME,SKILL_CATEGORY,SERVICE_REGION,PHONE,STATUS) " +
                "VALUES (?,?,?,?,?,?)";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, technician.getTechnicianID());
            ps.setString(2, technician.getFullName());
            ps.setString(3, technician.getSkillCategory());
            ps.setString(4, technician.getServiceRegion());
            ps.setString(5, technician.getPhone());
            ps.setString(6, technician.getStatus());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean updateTechnicianStatus(String technicianID, String status) throws SQLException {
        String sql = "UPDATE TECHNICIAN_TBL SET STATUS = ? WHERE TECHNICIAN_ID = ?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, technicianID);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean deleteTechnician(String technicianID) throws SQLException {
        String sql = "DELETE FROM TECHNICIAN_TBL WHERE TECHNICIAN_ID = ?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, technicianID);
            return ps.executeUpdate() == 1;
        }
    }
}
