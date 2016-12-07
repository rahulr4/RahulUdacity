
package com.rahul.udacity.cs2.ui.login;

import com.rahul.udacity.cs2.ui.common.CommonView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

interface LoginView extends CommonView {
    void navigateHome(ArrayList<LinkedHashMap> response);

    void loginFailed(String response);

}
