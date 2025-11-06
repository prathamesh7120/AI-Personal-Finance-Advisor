package com.aifinance.bean;

import java.time.LocalDate;

public class User {
	
	private int userId;
	private String username;
	private String email;
	private double monthlyIncome;
	private String financialGoal;
	private String riskTolerance;
	private LocalDate createdDate;
	
	public User() {
		super();
	}
	
	public User(int userId, String username, String email, double monthlyIncome, String financialGoal,
			String riskTolerance, LocalDate createdDate) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.monthlyIncome = monthlyIncome;
		this.financialGoal = financialGoal;
		this.riskTolerance = riskTolerance;
		this.createdDate = createdDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getMonthlyIncome() {
		return monthlyIncome;
	}
	public void setMonthlyIncome(double monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	public String getFinancialGoal() {
		return financialGoal;
	}
	public void setFinancialGoal(String financialGoal) {
		this.financialGoal = financialGoal;
	}
	public String getRiskTolerance() {
		return riskTolerance;
	}
	public void setRiskTolerance(String riskTolerance) {
		this.riskTolerance = riskTolerance;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	
	
	
}
