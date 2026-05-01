package com.insecurebank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testHardcodedCredentials() {
        User user = new User("admin", "password123");
        assertEquals("admin", user.username);
        assertEquals("password123", user.password);
    }

    @Test
    public void testPlainTextPasswordStorage() {
        User user = new User("testuser", "mypassword");
        // This test demonstrates the flaw: password is stored in plain text
        assertEquals("mypassword", user.password, "Password is stored in plain text!");
    }
}
