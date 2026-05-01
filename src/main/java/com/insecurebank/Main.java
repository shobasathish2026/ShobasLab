package com.insecurebank;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Welcome to Insecure Bank App ===");

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String user = sc.nextLine();

        System.out.print("Enter password: ");
        String pass = sc.nextLine();

        // Hardcoded credentials (static flaw)
        if(user.equals("admin") && pass.equals("password123")) {
            System.out.println("Login successful!");
            Database db = new Database();

            System.out.print("Enter account ID to view balance: ");
            String accountId = sc.nextLine();

            // Vulnerable to SQL Injection (dynamic flaw)
            String balance = db.getBalance(accountId);
            System.out.println("Balance for account " + accountId + " is: " + balance);

            // Logging sensitive info (static flaw)
            System.out.println("DEBUG: User " + user + " accessed account " + accountId);
        } else {
            System.out.println("Invalid credentials!");
        }

        sc.close();
    }
}
