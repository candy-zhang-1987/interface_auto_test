package com.lemon.json;

public class StudentInformation {
    private String name;
    private int age;
    private String gender;

    public StudentInformation(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public StudentInformation() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student2{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
