package com.example.chara.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Employee implements Serializable {
    private String id;

    private String name;

    private String surname;

    private Boolean sex;

    private Date born;

    private Double salary;

    private String patronymic;

    private String phone;

    private String address;

    private Employee chief;

    private Depart depart;

    private Post post;

    private Passport passport;

    public Employee(String id, String name, String surname, Double salary, String patronymic, String phone, String address)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.patronymic = patronymic;
        this.phone = phone;
        this.address = address;
    }

    public Employee(String fio, Post post)
    {
        String[] temp = fio.split(" ");
        this.post = post;
        this.name = temp[0];
        this.surname = temp[1];
        if (temp.length == 3)
            this.patronymic = temp[2];
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Date getBorn() {
        return born;
    }

    public void setBorn(Date born) {
        this.born = born;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Employee getChief() {
        return chief;
    }

    public void setChief(Employee chief) {
        this.chief = chief;
    }

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

}
