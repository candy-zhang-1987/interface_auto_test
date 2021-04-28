package com.lemon.HomeWork;

public class JsonValidate {
    private String value;
    private String expression;

    public JsonValidate(String value, String expression) {
        this.value = value;
        this.expression = expression;
    }

    public JsonValidate() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "JsonValidate{" +
                "value='" + value + '\'' +
                ", expression='" + expression + '\'' +
                '}';
    }
}
