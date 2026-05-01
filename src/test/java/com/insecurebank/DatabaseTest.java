package com.insecurebank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    @Test
    public void testValidAccountBalance() {
        Database db = new Database();
        String balance = db.getBalance("123");
        assertEquals("1000", balance, "Balance should match for account 123");
    }
/*
    @Test
    public void testInvalidAccountBalance() {
        Database db = new Database();
        String balance = db.getBalance("999");
        assertNull(balance, "Balance should be null for non-existent account");
    }
*/
    @Test
    public void testSqlInjectionAttempt() {
        Database db = new Database();
        // This is intentionally insecure: SQL injection should return unintended results
        String balance = db.getBalance("' OR '1'='1");
        assertNotNull(balance, "SQL injection returned a result, showing vulnerability");
    }
}
