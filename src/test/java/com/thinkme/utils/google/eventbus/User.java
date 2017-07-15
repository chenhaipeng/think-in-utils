package com.thinkme.utils.google.eventbus;

/**
 * @author chenhaipeng
 * @version 1.0
 * @date 2017/07/15 14:26
 */
public class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
