
package com.rahul.udacity.cs2.ui.register;

import com.rahul.udacity.cs2.ui.common.CommonView;

interface RegisterView extends CommonView {
    void navigateHome();

    void registerFailed(String response);

}
