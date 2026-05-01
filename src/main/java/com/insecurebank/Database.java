package com.insecurebank;

import java.sql.*;

public class Database {
    private Connection connect() {
        try {
            // H2 in-memory DB
            Connection conn = DriverManager.getConnection("jdbc:h2:mem:bankdb", "sa", "");
            initialize(conn);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initialize(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS accounts (id VARCHAR(255), balance VARCHAR(255))");
            stmt.execute("INSERT INTO accounts VALUES ('123', '1000')");
            stmt.execute("INSERT INTO accounts VALUES ('456', '2500')");
            stmt.execute("INSERT INTO accounts VALUES ('789', '500')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getBalance(String accountId) {
        String result = "0";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            // SQL Injection vulnerability (dynamic flaw)
            String query = "SELECT balance FROM accounts WHERE id = '" + accountId + "'";
            ResultSet rs = stmt.executeQuery(query);

            if(rs.next()) {
                result = rs.getString("balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
