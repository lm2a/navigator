package com.lm2a.navigator;

import android.os.Bundle;

/**
 * Created by lm2a on 17/07/2017.
 */

public interface NavegableFragment {
    NavegableFragment getInstance();
    void updateUserInterface(String event);
    void onSetProperties(Bundle bundle);
    String getEventUIConfigurationId();
    String getLastScreenBeforeCurrent();
    String getEventLeadingToCurrentScreen();
    String getCurrentScreen();
}
