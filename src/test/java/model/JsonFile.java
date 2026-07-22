package model;

import java.util.ArrayList;
import java.util.List;

public class JsonFile {
    private Integer userId;
    private String  userName;
    private Boolean isActive;
    private AddressInner address;
    private List<String> roles = new ArrayList<>();
    private String lastLogin;


    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

   public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public AddressInner getAddress() {
        return address;
    }

    public void setAddress(AddressInner address) {
        this.address = address;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}

