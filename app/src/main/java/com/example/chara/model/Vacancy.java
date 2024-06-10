package com.example.chara.model;

public class Vacancy {

    private String id;

    private String name;

    private String requirements;

    private String circs;

    private Depart depart;

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

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getCircs() {
        return circs;
    }

    public void setCircs(String circs) {
        this.circs = circs;
    }

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }
}
