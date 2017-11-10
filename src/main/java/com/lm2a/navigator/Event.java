package com.lm2a.navigator;

/**
 * Created by lm2a on 17/07/2017.
 */

public class Event {
    String eventName;
    String className;
    String nextScreen;

    public Event() {
    }

    public Event(String name, String className, String next) {
        this.eventName = name;
        this.className = className;
        this.nextScreen = next;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getNextScreen() {
        return nextScreen;
    }

    public void setNextScreen(String nextScreen) {
        this.nextScreen = nextScreen;
    }
}
