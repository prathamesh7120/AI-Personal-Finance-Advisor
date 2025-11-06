package com.aifinance.bean;

import java.time.LocalDate;

public class Transaction {
	
	private int transactionId;
	private int userId;
	private double amount;
	private String category;
	private String subcategory;
	private String description;
	private LocalDate transactionDate;
	private String transactionType;
	private String aiPredicatedCategory;
	private boolean isRecurring;
	
	public Transaction() {
		super();
	}
	
	public Transaction(int transactionId, int userId, double amount, String category, String subcategory,
			String description, LocalDate transactionDate, String transactionType, String aiPredicatedCategory,
			boolean isRecurring) {
		super();
		this.transactionId = transactionId;
		this.userId = userId;
		this.amount = amount;
		this.category = category;
		this.subcategory = subcategory;
		this.description = description;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
		this.aiPredicatedCategory = aiPredicatedCategory;
		this.isRecurring = isRecurring;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getAiPredicatedCategory() {
		return aiPredicatedCategory;
	}
	public void setAiPredicatedCategory(String aiPredicatedCategory) {
		this.aiPredicatedCategory = aiPredicatedCategory;
	}
	public boolean isRecurring() {
		return isRecurring;
	}
	public void setRecurring(boolean isRecurring) {
		this.isRecurring = isRecurring;
	}
	

}
