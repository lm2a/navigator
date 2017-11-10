package com.lm2a.navigator;

/**
 * Created by lm2a on 31/07/2017.
 */

public class Property {
    String name;
    String value;
    String type;

    public Property() {
    }

    public Property(String name, String value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
