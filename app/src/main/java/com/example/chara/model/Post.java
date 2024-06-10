package com.example.chara.model;

import java.io.Serializable;

public class Post implements Serializable {

    private String id;

    private String name;

    private Depart depart;

    public Post(String name, Depart depart)
    {
        this.name = name;
        this.depart = depart;
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

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }
}
