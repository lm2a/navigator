package com.lm2a.navigator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Created by lm2a on 17/07/2017.
 */

public class Navigator {
    public static final int ADD = 0;
    public static final int REPLACE = 1;
    public static final String TAG = "I&D";
    public static final String COME_FROM_SCREEN = "NavComeFrom";
    public static final String BY_EVENT = "NavByEvent";
    public static final String CURRENT_SCREEN = "NavCurrentScreen";
    public static final String BACK_EVENT = "BACK";

    private static NavigatorData mNavigatorData;
    private static Navigator instance = null;
    private static Context mContext;
    private static FragmentManager mFragmentManager;
    private static int mFragmentContainerLayout;

    private String mCurrentScreen;
    private String mLastScreen;
    private String mLastEvent;

    Stack mStack = new Stack();

    public static Navigator getInstance() {
        if (instance == null) {
            instance = new Navigator();
        }
        return instance;
    }

    public static void init(Context context, int fragmentContainerLayoutId){
        mContext = context;
        mFragmentContainerLayout = fragmentContainerLayoutId;
        //Get navigatorData from reading XML
        mNavigatorData = getNavigatorData();
        mFragmentManager = ((FragmentActivity)mContext).getSupportFragmentManager();
    }



    protected Navigator() {

    }


    private static NavigatorData getNavigatorData() {
        NavigatorParser navigatorParser = new NavigatorParser(mContext);
        NavigatorData navigatorData = navigatorParser.parse();
        return navigatorData;
    }


    public void next(String screen, String transition){
        if(transition.equals(BACK_EVENT)){
            NavigatorEntry currentScreen = getNavigatorEntryByTag(screen);
            if (currentScreen == null) {
                Log.e("I&D", "1 -  Error " + screen + " devolvio un entrada State inexistente ");
            }
            List<Event> transitionRow = currentScreen.transitions;

            for (Event event : transitionRow) {
                if (event.getEventName().equals(transition)) {
                    goBack();
                    return;
                }
            }
            Log.e("I&D", "1 -  Error " + screen + " intenta hacer BACK pero no tiene el evento definido ");
        }else {
            Event resultState = null;
            NavigatorEntry currentScreen = getNavigatorEntryByTag(screen);
            if (currentScreen == null) {
                Log.e("I&D", "1 -  Error " + screen + " devolvio un entrada State inexistente ");
            }
            List<Event> transitionRow = currentScreen.transitions;

            for (Event event : transitionRow) {
                if (event.getEventName().equals(transition)) {
                    resultState = event;
                }
            }
            if (resultState == null) {
                Log.e("I&D", "1 -  Error no encontro un Event para " + transition + " en " + currentScreen.screenName);
            }
            NavigatorEntry nextScreen = getNavigatorEntryByTag(resultState.nextScreen);
            if (nextScreen == null) {
                Log.e("I&D", "1 -  Error " + resultState.nextScreen + " devolvio un entrada State inexistente ");
            }
            Log.i("I&D", "1 -  Esta en " + screen + " y ocurre " + transition);

            //keeping state to be able to roll back in goBack() method
            mLastScreen = screen;
            mLastEvent = resultState.eventName;
            mCurrentScreen = resultState.nextScreen;

            BackItem bi = new BackItem(mLastScreen, mLastEvent, mCurrentScreen);
            mStack.push(bi);

            printStack(mStack, "NEXT "+screen+"/"+transition);

            Bundle bundle = getEntryProperties(nextScreen);
            bundle.putString(COME_FROM_SCREEN, screen);
            bundle.putString(BY_EVENT, transition);
            bundle.putString(CURRENT_SCREEN, resultState.nextScreen);
            loadFragment(getLastEvent(), getCurrentScreen(), nextScreen.screenNameClass, nextScreen.method, nextScreen.addBackStack, bundle);
        }
    }

    private void printStack(Stack s, String label){
        Log.i("I&D",label + " - " + Arrays.toString(s.toArray()));
    }

    //JTEYH20V800059487


    private Bundle getEntryProperties(NavigatorEntry navigatorEntry){
        Bundle bundle = new Bundle();
        List<Property> properties = navigatorEntry.properties;
        for(Property p: properties){
            if(p.getType().equals("String")){
                bundle.putString(p.getName(), p.getValue());
            }
            if(p.getType().equals("int")){
                bundle.putInt(p.getName(), getIntFromStringZeroNull(p.getValue()));
            }
            if(p.getType().equals("boolean")){
                bundle.putBoolean(p.getName(), getBooleanFromStringFalseNull(p.getValue()));
            }
        }
        return bundle;
    }




    private NavigatorEntry getNavigatorEntryByTag(String tag) {
        List<NavigatorEntry> transitionTable = mNavigatorData.transitionTable;
        for (NavigatorEntry ne : transitionTable) {
            if (ne.screenName.equals(tag)) {
                return ne;
            }
        }
        return null;
    }



    public void next(String transition){
        Event resultState = null;
        String nextClassToInstanciate = null;
        List<NavigatorEntry> transitionTable = mNavigatorData.transitionTable;

        NavigatorEntry firstEntry = transitionTable.get(0);
        Bundle bundle = getEntryProperties(firstEntry);

        mCurrentScreen = transition;

        loadFragment(firstEntry.screenName, firstEntry.screenName, firstEntry.screenNameClass, REPLACE, true, bundle);

    }


    private String getErrorMessage(String navMessage, String stdMessage){
        return navMessage + ": " + stdMessage;
    }

    //TODO implementarlo como un rollback de next(), es decir que vuelva atras pero pasando hacia atras los parametros de "donde estaba" y "que evento ocurrio".?
    private void goBack(){
        printStack(mStack, "BACK antes POP");

        BackItem bi = (BackItem) mStack.pop();

        NavigatorEntry previousNavigatorEntry = getNavigatorEntryByTag(bi.comesFrom);
        Bundle bundle = getEntryProperties(previousNavigatorEntry);
        bundle.putString(COME_FROM_SCREEN, bi.comesFrom);
        bundle.putString(BY_EVENT, BACK_EVENT);
        bundle.putString(CURRENT_SCREEN, bi.currentScreen);
        loadFragment(BACK_EVENT, previousNavigatorEntry.screenName, previousNavigatorEntry.screenNameClass, REPLACE, true, bundle);

        Log.i("I&D","ElectionsFragment - "+"goBack() - Going back from: "+bi.currentScreen);
        Log.i("I&D","ElectionsFragment - "+"goBack() - Event Leading Current Screen: "+BACK_EVENT);
        Log.i("I&D","ElectionsFragment - "+"goBack() - Going back to: "+previousNavigatorEntry.screenName);

    }

    public void loadFragment(String eventName, String fragmentTag, String className, int op, boolean addToBackstack, Bundle bundle) {
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentTag);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Log.i("I&D", "2 - Ocurre el evento "+eventName+ " y va al fragmento: "+fragmentTag+"("+className+")");
        Log.i("I&D", "op = "+op+" addBackStack = "+addToBackstack);
        if (fragment == null) {
            Log.i("I&D", "3 -  "+fragmentTag+ " no existe, lo crea");
            try {
                Class<?> clazz = Class.forName(className);
                NavegableFragment f = (NavegableFragment)clazz.newInstance();
                //f.updateUserInterface(eventName);//tiene sentido aqui? Tal vez en onCreate() deberia cojer el primer estado
                f.onSetProperties(bundle);
                fragment = (Fragment)f.getInstance();


            }catch(ClassNotFoundException cnfe){
                Log.e(TAG, getErrorMessage("Error loading fragment", cnfe.getMessage()));
            }catch(InstantiationException ie){
                Log.e(TAG, getErrorMessage("Error loading fragment", ie.getMessage()));
            }catch(IllegalAccessException iae){
                Log.e(TAG, getErrorMessage("Error loading fragment", iae.getMessage()));
            }
        }else{
            Log.i("I&D", "3 -  "+fragmentTag+ " ya existe, lo recupera");
            ((NavegableFragment) fragment).updateUserInterface(eventName);
            ((NavegableFragment)fragment).onSetProperties(bundle);
        }

        mCurrentScreen = fragmentTag;
        if(fragment!=null) {
            if (op == ADD) {
                transaction.add(mFragmentContainerLayout, fragment, fragmentTag);
            } else {
                if (!fragment.isVisible()) {
                    String x = mContext.getPackageName();
                    //int id = getPlaceHolderId();
                    transaction.replace(mFragmentContainerLayout, fragment, fragmentTag);
                }
            }
            if (addToBackstack) {
                transaction.addToBackStack(fragmentTag);
            }

            transaction.commit();
        }else{//Navigator doing nothing, next class doesn't exist
            Log.e("I&D", "3 -  "+className+ " no existe en la App");
        }
        }

    public static int getIntFromStringZeroNull(String integer){
        if(integer != null) {
            try {
                return Integer.parseInt(integer);
            } catch (NumberFormatException e) {
                return 0;
            }
        }else{
            return 0;
        }
    }

    public static boolean getBooleanFromStringFalseNull(String booleanValue){
        if(booleanValue != null) {
            try {
                return Boolean.parseBoolean(booleanValue);
            } catch (Exception e) {
                return false;
            }
        }else{
            return false;
        }
    }

    public String getCurrentScreen() {
        return mCurrentScreen;
    }

    public String getLastScreen() {
        return mLastScreen;
    }

    public String getLastEvent() {
        return mLastEvent;
    }

//        private int getPlaceHolderId(){
//            return mContext.getResources().getIdentifier("placeholder", "ïd", mContext.getPackageName());
//        }




    private class BackItem {

        public BackItem(String comesFrom, String event, String currentScreen) {
            this.comesFrom = comesFrom;
            this.event = event;
            this.currentScreen = currentScreen;
        }

        String comesFrom;
        String event;
        String currentScreen;

        @Override
        public String toString() {
            return "BackItem{" +
                    "comesFrom='" + comesFrom + '\'' +
                    ", event='" + event + '\'' +
                    ", currentScreen='" + currentScreen + '\'' +
                    '}';
        }
    }

}
