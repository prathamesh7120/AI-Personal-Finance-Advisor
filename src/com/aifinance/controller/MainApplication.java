package com.aifinance.controller;

public class MainApplication {
    public static void main(String[] args) {
        System.out.println("🚀 Starting AI Personal Finance Advisor...");
        System.out.println("===========================================");
        
        AIFinanceController appController = new AIFinanceController();
        appController.startApplication();
    }
}