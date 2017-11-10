package com.lm2a.navigator;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class NavigatorParser
{

    private static final String STATE = "state";
    private static final String NAME_CLASS = "nameClass";
    private static final String CURRENT_SCREEN = "currentScreen";
    private static final String METHOD = "method";
    private static final String ADD_TO_BACKSTACK = "addToBackStack";
    private static final String EVENT = "event";
    private static final String NEXT_SCREEN = "nextScreen";
    private static final String EVENT_NAME = "eventName";
    private static final String PROPERTY = "property";
    private static final String PROPERTY_NAME = "name";
    private static final String PROPERTY_VALUE = "value";
    private static final String PROPERTY_TYPE = "type";
    private static final String METHOD_REPLACE = "REPLACE";

    private Context mContext;
 
    public NavigatorParser(Context context)
    {
            this.mContext = context;
    }
 
    public NavigatorData parse()
    {
        InputStream is = mContext.getResources().openRawResource(getXMLConfig());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document dom = null;
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            dom = dBuilder.parse(is);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        dom.getDocumentElement().normalize();
        System.out.println("Root element :" + dom.getDocumentElement().getNodeName());
        NavigatorData navigatorData = new NavigatorData();
        List<NavigatorEntry> states = new ArrayList<NavigatorEntry>();

        NodeList labTestList = dom.getElementsByTagName(STATE);
        for (int i = 0; i < labTestList.getLength(); ++i) { //iteration over state tags
            NavigatorEntry navigatorEntry = new NavigatorEntry();
            Element labTest = (Element) labTestList.item(i);
            String nameTag = labTest.getAttribute(CURRENT_SCREEN);
            String classClass = labTest.getAttribute(NAME_CLASS);
            String addMethod = labTest.getAttribute(METHOD);
            String addBackstack = labTest.getAttribute(ADD_TO_BACKSTACK);
            //get all properties
            NodeList propertyNodeList = labTest.getElementsByTagName(PROPERTY);

            List<Property> propertyList = new ArrayList<Property>();

            for (int j = 0; j < propertyNodeList.getLength(); ++j) {//iteration over event tags
                Property property = new Property();
                Element prop = (Element) propertyNodeList.item(j);
                String name = prop.getAttribute(PROPERTY_NAME);
                String value = prop.getAttribute(PROPERTY_VALUE);
                String type = prop.getAttribute(PROPERTY_TYPE);
                property.setName(name);
                property.setValue(value);
                property.setType(type);

                propertyList.add(property);
            }
            //get all events
            NodeList eventNodeList = labTest.getElementsByTagName(EVENT);

            List<Event> eventList = new ArrayList<Event>();

            for (int j = 0; j < eventNodeList.getLength(); ++j) {//iteration over event tags
                Event event = new Event();
                Element value = (Element) eventNodeList.item(j);
                String tag = value.getAttribute(EVENT_NAME);
                String next = value.getAttribute(NEXT_SCREEN);
                event.setEventName(tag);
                event.setClassName(null);
                event.setNextScreen(next);

                eventList.add(event);
            }

            navigatorEntry.screenName = nameTag;
            navigatorEntry.screenNameClass = classClass;
            navigatorEntry.properties = propertyList;
            navigatorEntry.transitions = eventList;
            navigatorEntry.method = (addMethod.equals(METHOD_REPLACE))? Navigator.REPLACE: Navigator.ADD;
            navigatorEntry.addBackStack = addBackstack.equals("true");
            states.add(navigatorEntry);
        }
        navigatorData.transitionTable = states;

        return navigatorData;
    }

    private int getXMLConfig(){
        return mContext.getResources().getIdentifier("navigator", "raw", mContext.getPackageName());
    }

}