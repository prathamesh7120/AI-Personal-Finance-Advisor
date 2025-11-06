package com.aifinance.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ai_finance_db";
    private static final String USERNAME = "root"; // Change if needed
    private static final String PASSWORD = "root"; // Change to your MySQL password
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
    
    // Test connection method
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Database connection successful!");
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
            return false;
        }
    }
}