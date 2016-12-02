
package com.rahul.udacity.cs2.ui.login;

import com.rahul.udacity.cs2.ui.common.CommonView;

interface LoginView extends CommonView {
    void navigateHome();

    void loginFailed(String response);

}
