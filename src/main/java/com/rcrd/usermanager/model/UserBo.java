package com.rcrd.usermanager.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserBo userBo = (UserBo) o;

        return new EqualsBuilder()
                .append(id, userBo.id)
                .append(firstName, userBo.firstName)
                .append(password, userBo.password)
                .append(address, userBo.address)
                .append(email, userBo.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(firstName)
                .append(password)
                .append(address)
                .append(email)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("firstName", firstName)
                .append("password", password)
                .append("address", address)
                .append("email", email)
                .toString();
    }
}
