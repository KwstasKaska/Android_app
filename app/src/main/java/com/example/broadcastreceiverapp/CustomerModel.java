package com.example.broadcastreceiverapp;

public class CustomerModel {

    private int id;
    private String customerEmail;
    private String customerPassword;
    private String user_age;
    private String user_sex;

    //constructors

    public CustomerModel(int id, String customerEmail, String customerPassword, String user_age, String user_sex) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
        this.user_age = user_age;
        this.user_sex = user_sex;
    }

    public CustomerModel() {
    }

    //toString is necessary for printing the contents of a class object

    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + id +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerPassword='" + customerPassword + '\'' +
                ", customerAge=" + user_age +
                ", customerGender='" + user_sex + '\'' +
                '}';
    }

    //getters sand setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }
}
