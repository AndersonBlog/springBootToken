package com.wu.springBootToken.entity;

import java.io.Serializable;

/**
 * 描述:
 *
 * @author WUAN4
 * @create 2019-10-16 8:52 AM
 */

public class User implements Serializable {

    private String Id;
    private String username;
    private String password;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
