package com.app.facebooklibrary;

/**
 * callback interface
 *
 * @author ashok
 */
public interface FbCallback {
    /**
     * method will be called on facebookLogin after authenticating
     *
     * @param beanObject : facebook bean object containing user data from facebook
     */
    public void onLoginSuccess(FBBean beanObject);


    /**
     * method will be called on facebookLogin failure
     *
     * @param message failure message
     */
    public void onLoginFailure(String message);


    /**
     * method will called on successfull post
     *
     * @param postID  id of the post
     * @param message post message
     */
    public void onPostSuccess(String postID, String message);

    /**
     * method will be called on failure in case of posting data
     *
     * @param message - post failure message
     */
    public void onPostFailure(String message);

    /**
     * method will be called on FacebookLogout
     */
    public void onLogout();

}
