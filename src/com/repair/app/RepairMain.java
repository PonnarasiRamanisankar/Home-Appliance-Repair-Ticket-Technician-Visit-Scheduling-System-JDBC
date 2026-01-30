package com.repair.app;

import com.repair.bean.Customer;
import com.repair.bean.Technician;
import com.repair.service.RepairService;
import com.repair.util.ActiveTicketsExistException;
import com.repair.util.TechnicianNotSuitableException;
import com.repair.util.ValidationException;

import java.sql.Date;
import java.util.Scanner;

public class RepairMain {

    private static RepairService repairService;

    public static void main(String[] args) {
        repairService = new RepairService();
        Scanner sc = new Scanner(System.in);

        System.out.println("--- Home Appliance Repair Ticket Console ---");

        // DEMO 1: Register a new customer
        try {
            Customer c = new Customer();
            c.setCustomerID("CUS2001");
            c.setFullName("Meera Krishnan");
            c.setAddressLine("Flat 8C, Riverfront Residency");
            c.setCity("Chennai");
            c.setPincode("600100");
            c.setPrimaryPhone("9876599999");
            c.setStatus("ACTIVE");
            boolean ok = repairService.registerNewCustomer(c);
            System.out.println(ok ? "CUSTOMER REGISTERED" : "CUSTOMER REGISTRATION FAILED");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.toString());
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }

        // DEMO 2: Register a new technician
        try {
            Technician t = new Technician();
            t.setTechnicianID("TEC3001");
            t.setFullName("Ravi Shankar");
            t.setSkillCategory("Refrigerator");
            t.setServiceRegion("Chennai-South");
            t.setPhone("9000090000");
            t.setStatus("ACTIVE");
            boolean ok = repairService.registerNewTechnician(t);
            System.out.println(ok ? "TECHNICIAN REGISTERED" : "TECHNICIAN REGISTRATION FAILED");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.toString());
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }

        // DEMO 3: Create a service ticket
        try {
            Date ticketDate = new Date(System.currentTimeMillis());
            boolean ok = repairService.createServiceTicket("CUS2001",
                    "Refrigerator", "Not cooling", ticketDate);
            System.out.println(ok ? "TICKET CREATED" : "TICKET CREATION FAILED");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.toString());
        } catch (Exception e) {
            System.out.println("System Error: " + e.getMessage());
        }

        sc.close();
    }
}
