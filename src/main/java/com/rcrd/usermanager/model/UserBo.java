package com.rcrd.usermanager.model;

public class UserBo {

    private Long id;
    private String firstName;
    private String password;
    private String address;
    private String email;

    public UserBo() {
    }

    public UserBo(String firstName, String password, String address, String email) {
        this.firstName = firstName;
        this.password = password;
        this.address = address;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
