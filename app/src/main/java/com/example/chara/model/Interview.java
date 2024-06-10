package com.example.chara.model;

import java.io.Serializable;
import java.util.Date;

public class Interview implements Serializable {

    private String id;
    private Date date;

    private Boolean passed;

    private Resume resume;

    private Vacancy vacancy;

    private Employee interviewer;

    public Interview(String id, Date date, Resume resume, Vacancy vacancy, Employee interviewer) {
        this.id = id;
        this.date = date;
        this.resume = resume;
        this.vacancy = vacancy;
        this.interviewer = interviewer;
    }

    public Interview(Date date, Resume resume, Employee interviewer)
    {
        this.date = date;
        this.resume = resume;
        this.interviewer = interviewer;
        this.vacancy = resume.getVacancy();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public Employee getInterviewer() {
        return interviewer;
    }

    public void setInterviewer(Employee interviewer) {
        this.interviewer = interviewer;
    }
}
