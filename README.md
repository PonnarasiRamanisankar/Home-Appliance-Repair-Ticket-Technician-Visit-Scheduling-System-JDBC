🛠️ Home Appliance Repair Ticket & Technician Visit Scheduling System (JDBC)

A Java console-based application developed using JDBC and Oracle Database to manage home appliance repair services.
The system handles customer registration, technician management, service ticket creation, technician assignment, visit scheduling, and ticket closure with proper validations and exception handling.

📌 Features
👤 Customer Management

            Register new customers
            
            View customer details
            
            View all customers

            Delete customers (only if no active tickets)

👨‍🔧 Technician Management

            Register technicians with skill category & service region
            
            View technician details
            
            View all technicians
            
            Delete technicians (only if no active tickets)

🎫 Service Ticket Management

            Create service tickets for appliances
            
            Auto-generate ticket IDs
            
            Assign suitable technicians
            
            Schedule technician visits
            
            Update visit outcome & service charges
            
            Close tickets or mark as waiting for parts

⚠️ Business Rules & Validations

            Prevent duplicate customer/technician IDs
            
            Ensure technician skill matches appliance type
            
            Prevent deletion when active tickets exist
            
            Custom exception handling for validations

🏗️ Project Structure
                    com.repair
                │
                ├── app
                │   └── RepairMain.java
                │
                ├── bean
                │   ├── Customer.java
                │   ├── Technician.java
                │   └── ServiceTicket.java
                │
                ├── dao
                │   ├── CustomerDAO.java
                │   ├── TechnicianDAO.java
                │   └── ServiceTicketDAO.java
                │
                ├── service
                │   └── RepairService.java
                │
                └── util
                    ├── DBUtil.java
                    ├── ValidationException.java
                    ├── TechnicianNotSuitableException.java
                    └── ActiveTicketsExistException.java

Output:

<img width="734" height="245" alt="image" src="https://github.com/user-attachments/assets/1fcaf27c-663a-44c9-b1b6-a5f982095bdd" />

If already Exist then:
<img width="837" height="345" alt="image" src="https://github.com/user-attachments/assets/31b2e9ee-cb3c-4730-9b8d-5a72a2a51ef5" />

