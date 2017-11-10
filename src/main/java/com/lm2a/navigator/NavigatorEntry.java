package com.lm2a.navigator;

import java.util.List;

/**
 * Created by lm2a on 17/07/2017.
 */

public class NavigatorEntry {
    public static final int FRAGMENT_V4 = 0;

    String screenName;  //nameTag
    String screenNameClass;
    int method;
    boolean addBackStack;
    List<Property> properties;
    List<Event> transitions;

    public NavigatorEntry(String screenName, String screenNameClass, int method, boolean addToBackStack, List<Property> properties, List<Event> transitions) {
        this.screenName = screenName;
        this.screenNameClass = screenNameClass;
        this.method = method;
        this.addBackStack = addToBackStack;
        this.properties = properties;
        this.transitions = transitions;

    }

    public NavigatorEntry() {

    }
}
