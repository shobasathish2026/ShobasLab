package com.insecurebank;

public class User {
    public String username;
    public String password; // Stored in plain text (static flaw)

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
