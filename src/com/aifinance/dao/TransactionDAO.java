package com.aifinance.dao;

import com.aifinance.bean.Transaction;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    
    // Add a new transaction with AI category prediction
    public boolean addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, amount, category, description, transaction_date, transaction_type, ai_predicted_category, is_recurring) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, transaction.getUserId());
            pstmt.setDouble(2, transaction.getAmount());
            pstmt.setString(3, transaction.getCategory());
            pstmt.setString(4, transaction.getDescription());
            pstmt.setDate(5, Date.valueOf(transaction.getTransactionDate()));
            pstmt.setString(6, transaction.getTransactionType());
            pstmt.setString(7, predictCategoryAI(transaction)); // AI prediction
            pstmt.setBoolean(8, transaction.isRecurring());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Error adding transaction: " + e.getMessage());
            return false;
        }
    }
    
    // AI-Powered Method: Predict category based on description
    private String predictCategoryAI(Transaction transaction) {
        String description = transaction.getDescription().toLowerCase();
        
        // Simple AI logic - in real world, this would use ML models
        if (description.contains("salary") || description.contains("paycheck")) {
            return "Income";
        } else if (description.contains("food") || description.contains("restaurant") || description.contains("grocery")) {
            return "Food & Dining";
        } else if (description.contains("rent") || description.contains("mortgage")) {
            return "Housing";
        } else if (description.contains("uber") || description.contains("taxi") || description.contains("fuel")) {
            return "Transportation";
        } else if (description.contains("netflix") || description.contains("spotify") || description.contains("subscription")) {
            return "Entertainment";
        } else {
            return "Other";
        }
    }
    
    // Get all transactions for a user
    public List<Transaction> getTransactionsByUser(int userId) {
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY transaction_date DESC";
        List<Transaction> transactions = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setUserId(rs.getInt("user_id"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setCategory(rs.getString("category"));
                transaction.setDescription(rs.getString("description"));
                transaction.setTransactionDate(rs.getDate("transaction_date").toLocalDate());
                transaction.setTransactionType(rs.getString("transaction_type"));
                transaction.setAiPredicatedCategory(rs.getString("ai_predicted_category"));
                transaction.setRecurring(rs.getBoolean("is_recurring"));
                
                transactions.add(transaction);
            }
            
        } catch (SQLException e) {
            System.out.println("Error getting transactions: " + e.getMessage());
        }
        return transactions;
    }
    
    // AI-Powered Method: Get monthly spending analysis
    public double getMonthlySpending(int userId, String category) {
        String sql = "SELECT SUM(amount) as total FROM transactions WHERE user_id = ? AND category = ? AND transaction_type = 'EXPENSE' AND MONTH(transaction_date) = MONTH(CURRENT_DATE())";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            pstmt.setString(2, category);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.out.println("Error calculating monthly spending: " + e.getMessage());
        }
        return 0.0;
    }
}
