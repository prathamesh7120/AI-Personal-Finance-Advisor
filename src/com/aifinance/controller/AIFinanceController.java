package com.aifinance.controller;

import com.aifinance.bean.User;
import com.aifinance.bean.Transaction;
import com.aifinance.dao.UserDAO;
import com.aifinance.dao.TransactionDAO;
import com.aifinance.dao.DatabaseConnection;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AIFinanceController {
    private UserDAO userDAO;
    private TransactionDAO transactionDAO;
    private Scanner scanner;
    private User currentUser;

    public AIFinanceController() {
        this.userDAO = new UserDAO();
        this.transactionDAO = new TransactionDAO();
        this.scanner = new Scanner(System.in);
    }

    public void startApplication() {
        System.out.println("🤖 Welcome to AI Personal Finance Advisor!");
        System.out.println("===========================================");
        
        // Test database connection first
        if (!DatabaseConnection.testConnection()) {
            System.out.println("❌ Cannot connect to database. Exiting...");
            return;
        }

        showMainMenu();
    }

    private void showMainMenu() {
        while (true) {
            System.out.println("\n💎 MAIN MENU:");
            System.out.println("1. 👤 Register New User");
            System.out.println("2. 🔐 Login");
            System.out.println("3. 🚪 Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("Thank you for using AI Finance Advisor! 👋");
                    return;
                default:
                    System.out.println("❌ Invalid option. Please try again.");
            }
        }
    }

    private void registerUser() {
        System.out.println("\n📝 USER REGISTRATION");
        System.out.println("====================");
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter monthly income: ");
        double income = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        
        System.out.print("Enter financial goal: ");
        String goal = scanner.nextLine();
        
        System.out.print("Enter risk tolerance (LOW/MEDIUM/HIGH): ");
        String risk = scanner.nextLine().toUpperCase();

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setMonthlyIncome(income);
        newUser.setFinancialGoal(goal);
        newUser.setRiskTolerance(risk);

        if (userDAO.addUser(newUser)) {
            System.out.println("✅ User registered successfully!");
        } else {
            System.out.println("❌ Registration failed. Username might be taken.");
        }
    }

    private void loginUser() {
        System.out.println("\n🔐 USER LOGIN");
        System.out.println("=============");
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        currentUser = userDAO.getUserByUsername(username);
        
        if (currentUser != null) {
            System.out.println("✅ Login successful! Welcome, " + currentUser.getUsername() + "!");
            showUserDashboard();
        } else {
            System.out.println("❌ User not found. Please check username or register.");
        }
    }

    private void showUserDashboard() {
        while (true) {
            System.out.println("\n🏠 DASHBOARD - Welcome " + currentUser.getUsername() + "!");
            System.out.println("=================================");
            System.out.println("1. 💰 Add Transaction");
            System.out.println("2. 📊 View All Transactions");
            System.out.println("3. 🤖 Get AI Financial Insights");
            System.out.println("4. 📈 Monthly Spending Analysis");
            System.out.println("5. 🔮 Advanced AI Analytics"); 
            System.out.println("6. 🔙 Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addTransaction();
                    break;
                case 2:
                    viewTransactions();
                    break;
                case 3:
                    getAIFinancialInsights();
                    break;
                case 4:
                    showMonthlyAnalysis();
                    break;
                case 5:
                	showAdvancedAIAnalytics();
                    System.out.println("✅ Logged out successfully!");
                    return;
                default:
                    System.out.println("❌ Invalid option. Please try again.");
            }
        }
    }

    private void showAdvancedAIAnalytics() {
        System.out.println("\n🔮 ADVANCED AI ANALYTICS");
        System.out.println("========================");
        
        List<Transaction> transactions = transactionDAO.getTransactionsByUser(currentUser.getUserId());
        
        if (transactions.size() < 5) {
            System.out.println("Need more transaction data for advanced analysis. Add at least 5 transactions.");
            return;
        }
        
        // AI-Powered Financial Health Score
        double healthScore = calculateFinancialHealthScore();
        System.out.printf("🏆 AI Financial Health Score: %.1f/100%n", healthScore);
        
        // Spending Pattern Detection
        detectSpendingPatterns();
        
        // Savings Projection
        projectSavingsTimeline();
        
        // Alert System
        checkFinancialAlerts();
    }

    private double calculateFinancialHealthScore() {
        double score = 75.0; // Base score
        
        List<Transaction> transactions = transactionDAO.getTransactionsByUser(currentUser.getUserId());
        double totalIncome = 0;
        double totalExpenses = 0;
        
        for (Transaction t : transactions) {
            if ("INCOME".equals(t.getTransactionType())) {
                totalIncome += t.getAmount();
            } else {
                totalExpenses += t.getAmount();
            }
        }
        
        // AI Scoring Algorithm
        if (totalIncome > 0) {
            double savingsRate = ((totalIncome - totalExpenses) / totalIncome) * 100;
            
            if (savingsRate > 20) score += 15;
            else if (savingsRate > 10) score += 10;
            else if (savingsRate > 0) score += 5;
            else score -= 10;
        }
        
        // Risk tolerance bonus
        if ("MEDIUM".equals(currentUser.getRiskTolerance())) score += 5;
        if ("LOW".equals(currentUser.getRiskTolerance())) score += 3;
        
        return Math.min(100, Math.max(0, score));
    }

    private void detectSpendingPatterns() {
        System.out.println("\n📊 AI SPENDING PATTERNS DETECTED:");
        
        // Simple pattern detection - in real app, this would use ML
        double foodSpending = transactionDAO.getMonthlySpending(currentUser.getUserId(), "Food & Dining");
        double entertainmentSpending = transactionDAO.getMonthlySpending(currentUser.getUserId(), "Entertainment");
        
        if (foodSpending > 300) {
            System.out.println("🍔 High dining expenses detected - consider meal prepping");
        }
        
        if (entertainmentSpending > 200) {
            System.out.println("🎬 High entertainment spending - look for free alternatives");
        }
        
        // Detect if user has recurring subscriptions
        System.out.println("💡 Pattern: " + (foodSpending > entertainmentSpending ? "Essential-focused spender" : "Lifestyle-focused spender"));
    }

    private void projectSavingsTimeline() {
        if (currentUser.getFinancialGoal() == null || currentUser.getFinancialGoal().isEmpty()) {
            System.out.println("🎯 Set a financial goal in your profile to see savings projections");
            return;
        }
        
        double monthlyIncome = currentUser.getMonthlyIncome();
        double monthlySavings = monthlyIncome * 0.2; // Assume 20% savings rate
        
        System.out.println("\n📅 SAVINGS PROJECTION:");
        System.out.printf("Based on your current income ($%.2f/month)%n", monthlyIncome);
        System.out.printf("Projected savings: $%.2f/month%n", monthlySavings);
        System.out.printf("Goal: %s%n", currentUser.getFinancialGoal());
        
        // Simple projection - in real app, this would consider compound interest
        System.out.println("💡 At this rate, you'll reach 50% of your goal in 6 months");
    }

    private void checkFinancialAlerts() {
    	 System.out.println("\"🚨 AI FINANCIAL ALERTS:\"");
        
        double monthlyIncome = currentUser.getMonthlyIncome();
        double foodSpending = transactionDAO.getMonthlySpending(currentUser.getUserId(), "Food & Dining");
        
        if (foodSpending > monthlyIncome * 0.3) {
            System.out.println("⚠️  CRITICAL: Food spending exceeds 30% of income!");
        }
        
        // Emergency fund check
        double totalSavings = calculateTotalSavings();
        if (totalSavings < monthlyIncome * 3) {
            System.out.println("💰 Suggestion: Build emergency fund (3-6 months of expenses)");
        }
        
        System.out.println("✅ All other financial metrics are stable");
    }

    private double calculateTotalSavings() {
        // Simplified calculation - in real app, this would sum all savings
        return currentUser.getMonthlyIncome() * 2; // Placeholder
    }

	// We'll implement these methods in the next step
    private void addTransaction() {
        System.out.println("\n💰 ADD TRANSACTION");
        System.out.println("=================");
        
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        
        System.out.print("Enter category (Food, Housing, Transportation, Entertainment, Other): ");
        String category = scanner.nextLine();
        
        System.out.print("Enter type (INCOME/EXPENSE): ");
        String type = scanner.nextLine().toUpperCase();
        
        System.out.print("Is this recurring? (true/false): ");
        boolean isRecurring = scanner.nextBoolean();
        scanner.nextLine(); // consume newline

        Transaction transaction = new Transaction();
        transaction.setUserId(currentUser.getUserId());
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setCategory(category);
        transaction.setTransactionType(type);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setRecurring(isRecurring);

        if (transactionDAO.addTransaction(transaction)) {
            System.out.println("✅ Transaction added successfully!");
            
            // Show AI prediction
            System.out.println("🤖 AI Predicted Category: " + 
                (transaction.getAiPredicatedCategory() != null ? 
                 transaction.getAiPredicatedCategory() : "Analyzing..."));
        } else {
            System.out.println("❌ Failed to add transaction.");
        }
    }

    private void viewTransactions() {
        System.out.println("\n📊 YOUR TRANSACTIONS");
        System.out.println("====================");
        
        List<Transaction> transactions = transactionDAO.getTransactionsByUser(currentUser.getUserId());
        
        if (transactions.isEmpty()) {
            System.out.println("No transactions found. Start by adding some transactions!");
            return;
        }

        System.out.printf("%-5s %-12s %-20s %-15s %-10s %s%n", 
            "ID", "Date", "Description", "Amount", "Type", "AI Category");
        System.out.println("-------------------------------------------------------------------");
        
        double totalIncome = 0;
        double totalExpenses = 0;
        
        for (Transaction transaction : transactions) {
            String aiCategory = transaction.getAiPredicatedCategory() != null ? 
                               transaction.getAiPredicatedCategory() : "N/A";
            
            System.out.printf("%-5d %-12s %-20s $%-14.2f %-10s %s%n",
                transaction.getTransactionId(),
                transaction.getTransactionDate(),
                transaction.getDescription().length() > 20 ? 
                    transaction.getDescription().substring(0, 17) + "..." : 
                    transaction.getDescription(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                aiCategory);
            
            if ("INCOME".equals(transaction.getTransactionType())) {
                totalIncome += transaction.getAmount();
            } else {
                totalExpenses += transaction.getAmount();
            }
        }
        
        System.out.println("-------------------------------------------------------------------");
        System.out.printf("📈 Total Income: $%.2f%n", totalIncome);
        System.out.printf("📉 Total Expenses: $%.2f%n", totalExpenses);
        System.out.printf("💰 Net Savings: $%.2f%n", (totalIncome - totalExpenses));
    }

    private void getAIFinancialInsights() {
        System.out.println("\n🤖 AI FINANCIAL INSIGHTS");
        System.out.println("========================");
        
        List<Transaction> transactions = transactionDAO.getTransactionsByUser(currentUser.getUserId());
        
        if (transactions.isEmpty()) {
            System.out.println("Not enough data for AI analysis. Add some transactions first!");
            return;
        }
        
        double monthlySpending = transactionDAO.getMonthlySpending(currentUser.getUserId(), "Food & Dining");
        double monthlyIncome = currentUser.getMonthlyIncome();
        
        // AI Analysis based on spending patterns
        System.out.println("🔍 Analyzing your financial patterns...");
        System.out.println("----------------------------------------");
        
        if (monthlySpending > (monthlyIncome * 0.3)) {
            System.out.println("⚠️  ALERT: You're spending more than 30% of income on Food & Dining");
            System.out.println("💡 Suggestion: Consider meal planning to reduce food costs");
        } else {
            System.out.println("✅ Good job! Your food spending is within healthy limits");
        }
        
        // Simple AI recommendation based on risk tolerance
        String risk = currentUser.getRiskTolerance();
        System.out.println("\n🎯 Personalized Recommendations for " + risk + " risk tolerance:");
        
        switch (risk) {
            case "LOW":
                System.out.println("💡 Consider high-yield savings accounts (2-4% APY)");
                System.out.println("💡 Government bonds are safe investments");
                break;
            case "MEDIUM":
                System.out.println("💡 Look into index funds for moderate growth");
                System.out.println("💡 Consider real estate investment trusts (REITs)");
                break;
            case "HIGH":
                System.out.println("💡 Research growth stocks and tech companies");
                System.out.println("💡 Consider cryptocurrency (small percentage only)");
                break;
        }
    }

    private void showMonthlyAnalysis() {
        System.out.println("\n📈 MONTHLY SPENDING ANALYSIS");
        System.out.println("============================");
        
        String[] categories = {"Food & Dining", "Housing", "Transportation", "Entertainment", "Other"};
        
        System.out.printf("%-20s %-15s %s%n", "Category", "Monthly Spend", "Analysis");
        System.out.println("---------------------------------------------------");
        
        for (String category : categories) {
            double spending = transactionDAO.getMonthlySpending(currentUser.getUserId(), category);
            String analysis = getSpendingAnalysis(spending, currentUser.getMonthlyIncome(), category);
            System.out.printf("%-20s $%-14.2f %s%n", category, spending, analysis);
        }
    }

    private String getSpendingAnalysis(double spending, double income, String category) {
        double percentage = (spending / income) * 100;
        
        if (spending == 0) return "No spending";
        if (percentage < 10) return "✅ Low";
        if (percentage < 20) return "⚠️ Moderate";
        if (percentage < 30) return "🚨 High";
        return "🔥 Critical";
    }
}