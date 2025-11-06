package com.aifinance.dao;

import com.aifinance.bean.User;
import java.sql.*;
import java.time.LocalDate;

public class UserDAO {
    
    // Method to add a new user with financial profile
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (username, email, monthly_income, financial_goal, risk_tolerance, created_date) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setDouble(3, user.getMonthlyIncome());
            pstmt.setString(4, user.getFinancialGoal());
            pstmt.setString(5, user.getRiskTolerance());
            pstmt.setDate(6, Date.valueOf(LocalDate.now()));
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
            return false;
        }
    }
    
    // Method to get user by username (for login)
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setMonthlyIncome(rs.getDouble("monthly_income"));
                user.setFinancialGoal(rs.getString("financial_goal"));
                user.setRiskTolerance(rs.getString("risk_tolerance"));
                user.setCreatedDate(rs.getDate("created_date").toLocalDate());
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting user: " + e.getMessage());
        }
        return user;
    }
    
    // AI-Powered Method: Update user's risk tolerance based on spending patterns
    public boolean updateRiskTolerance(int userId, String riskLevel) {
        String sql = "UPDATE users SET risk_tolerance = ? WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, riskLevel);
            pstmt.setInt(2, userId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error updating risk tolerance: " + e.getMessage());
            return false;
        }
    }
}