package com.cardss.util;

import java.util.HashMap;
import java.util.Map;

public class AuthManager {

    private static final Map<String, String[]> USERS = new HashMap<>();
    private static String currentUser = null;

    static {
        USERS.put("admin", new String[]{"admin123", "admin@cardss.com"});
        USERS.put("demo",  new String[]{"demo123",  "demo@cardss.com"});
    }

    public static String register(String username, String email, String password) {
        if (username == null || username.trim().isEmpty())
            return "Username cannot be empty.";
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"))
            return "Invalid email address.";
        if (password == null || password.length() < 6)
            return "Password must be at least 6 characters.";
        if (USERS.containsKey(username.toLowerCase()))
            return "Username already exists.";

        USERS.put(username.toLowerCase(), new String[]{password, email});
        return null;
    }

    public static String login(String username, String password) {
        if (username == null || password == null)
            return "Username and password required.";
        String[] creds = USERS.get(username.toLowerCase());
        if (creds == null)
            return "User not found.";
        if (!creds[0].equals(password))
            return "Incorrect password.";
        currentUser = username.toLowerCase();
        return null;
    }

    public static void logout() { currentUser = null; }

    public static boolean isLoggedIn() { return currentUser != null; }

    public static String getCurrentUser() { return currentUser; }

    public static String getEmail(String username) {
        String[] creds = USERS.get(username.toLowerCase());
        return creds != null ? creds[1] : "";
    }
}
