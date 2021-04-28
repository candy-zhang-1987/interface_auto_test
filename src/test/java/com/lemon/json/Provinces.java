package com.lemon.json;

public class Provinces {
    private String name;
    private String capital;

    public Provinces(String name, String capital) {
        this.name = name;
        this.capital = capital;
    }

    public Provinces() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    @Override
    public String toString() {
        return "Provinces{" +
                "name='" + name + '\'' +
                ", capital='" + capital + '\'' +
                '}';
    }
}
