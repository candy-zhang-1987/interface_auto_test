package com.lemon.json;

import java.util.List;

public class Country {
    private String name;
    private List<Provinces> provinces;

    public Country(String name, List<Provinces> provinces) {
        this.name = name;
        this.provinces = provinces;
    }

    public Country() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Provinces> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Provinces> provinces) {
        this.provinces = provinces;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", provinces=" + provinces +
                '}';
    }
}
