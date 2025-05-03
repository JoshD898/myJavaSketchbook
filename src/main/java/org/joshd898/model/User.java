package org.joshd898.model;

public class User {
    private long userID;
    private String username;
    private String password;

    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
