package com.joshd898.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUser {
    private User user;

    @BeforeEach
    void runBefore() {
        user = new User("testUser", "password");
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("testUser", user.getUsername());
        assertEquals("password", user.getPassword());
        user.setUserID(999);
        assertEquals(999, user.getUserID());
    }
}
