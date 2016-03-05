package com.epam.preprod.bohdanov.model.entity;

import java.util.Date;

public class User extends Entity {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Faculty faculty;
    private String login;
    private String password;
    private String image;
    private Integer roleId;
    private Date lastLogon;
    private int failLoginCount;
    private Date bannedTo;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private boolean delivery;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public Date getLastLogon() {
        return lastLogon;
    }

    public void setLastLogon(Date lastLogon) {
        this.lastLogon = lastLogon;
    }

    public int getFailLoginCount() {
        return failLoginCount;
    }

    public void setFailLoginCount(int failLoginCount) {
        this.failLoginCount = failLoginCount;
    }

    public Date getBannedTo() {
        return bannedTo;
    }

    public void setBannedTo(Date bannedTo) {
        this.bannedTo = bannedTo;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
