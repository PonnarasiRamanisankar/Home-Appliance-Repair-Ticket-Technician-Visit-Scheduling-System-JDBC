package com.repair.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    public static Connection getDBConnection() {
        Connection connection = null;

        try {
             
            Class.forName("oracle.jdbc.driver.OracleDriver");

           
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String username = "PONNARASI";
            String password = "ponnarasi.rjk@007";

            connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database connection error");
            e.printStackTrace();
        }

        return connection;
    }
}
