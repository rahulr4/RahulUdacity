package com.rahul.udacity.cs2.utility;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rahul.udacity.cs2.R;
import com.rahul.udacity.cs2.custom_ui.CustomToast;
import com.rahul.udacity.cs2.custom_ui.MaterialProgressDialog;
import com.rahul.udacity.cs2.interfaces.callbacks.OkCancelCallback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * Utility class of Rental Host
 * Created by rahul on 9/7/15.
 */
public class Utility {

    private static final String EMPTY_REGEX = "^(?=\\s*\\S).*$";
    private static final String SPACES_REGEX = "^\\s*$";
    private static final String EMAIL_REGEX = "^([0-9a-zA-Z]([-\\.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$";
    //    private static final String MIN_SIX_REGEX = "^([a-zA-Z0-9@*#]{6,})$";
    private static final String MIN_SIXTEEN_REGEX = "^[0-9]{16,}$";
    private static final String PHONE_REGEX = "^[+#*\\(\\)\\[\\]]*([0-9][ ext+-pw#*\\(\\)\\[\\]]*){5,13}$";
    private static final String NAME_REGEX = "[a-zA-Z_0-9|.{0,2}]{3,}";
    private static final String MIN_SIX_REGEX = "^.{6,}$";
    private static final String MIN_NAME_SIX_REGEX = "^.{3,}$";
    private static AlertDialog mAlertDialog;

    /**
     * Method to put string value in shared preference
     *
     * @param context Context of the calling class
     * @param key     Key in which value to store
     * @param value   String value to be stored
     */
    public static void putStringValueInSharedPreference(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static void putIntValueInSharedPreference(Context context, String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    /**
     * Method to put long value in shared preference
     *
     * @param context Context of the calling class
     * @param key     Key in which value to store
     * @param value   Long value to be stored
     */
    public static void putLongValueInSharedPreference(Context context, String key, long value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * Method to put double value in shared preference
     *
     * @param context Context of the calling class
     * @param key     Key in which value to store
     * @param value   Long value to be stored
     */
    public static void putDoubleValueInSharedPreference(Context context, String key, double value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, (float) value);
        editor.commit();
    }

    /**
     * Method to put boolean value in shared preference
     *
     * @param context Context of the calling class
     * @param key     Key in which value to store
     * @param value   Boolean value to be stored
     */
    public static void putBooleanValueInSharedPreference(Context context, String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String doubleToStringNoDecimal(double d) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###.##");
        return formatter.format(d);
    }

    /**
     * Method to get string value from shared preference
     *
     * @param context Context of the calling class
     * @param param   Key from which value is retrieved
     */
    public static String getStringSharedPreference(Context context, String param) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(param, "");
    }

    /**
     * Method to get string value from shared preference
     *
     * @param context Context of the calling class
     * @param param   Key from which value is retrieved
     */
    public static Float getFloatSharedPreference(Context context, String param) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getFloat(param, 0.0f);
    }

    public static int getIntFromSharedPreference(Context context, String param) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(param, 0);
    }

    /**
     * Method to get long value from shared preference
     *
     * @param context Context of the calling class
     * @param param   Key from which value is retrieved
     */
    public static long getLongSharedPreference(Context context, String param) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(param, 0);
    }

    /**
     * Method to get boolean value from shared preference
     *
     * @param context Context of the calling class
     * @param param   Key from which value is retrieved
     */
    public static boolean getBooleanSharedPreference(Context context, String param) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(param, false);
    }

    /**
     * Method to clear shared preference key value
     *
     * @param context Context of the calling class
     * @param key     Key from which value is to be cleared
     */
    public static void clearSharedPrefData(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * Method to clear all shared preference key value
     *
     * @param context Context of the calling class
     */
    public static void clearAllSharedPrefData(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * Static method to get an instance of material styled progress dialog
     *
     * @param mContext Context of the calling class
     * @return An instance of MaterialProgressDialog
     */
    public static MaterialProgressDialog getProgressDialogInstance(Context mContext) {
        MaterialProgressDialog mProgressDialog = new MaterialProgressDialog(mContext,
                mContext.getString(R.string.loading));
        mProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mProgressDialog.setCancelable(false);
        return mProgressDialog;
    }

    /**
     * Static method to perform call intent operation
     *
     * @param mContext Context of the calling class
     * @param phone    contact number
     */

    public static void callPhone(Context mContext, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        mContext.startActivity(intent);
    }

    /**
     * This method is depreciated
     * use<code>ApplicationController.getApplicationInstance().isNetworkConnected()</code>
     *
     * @see Utility#getNetworkState(Context context)
     */
    @Deprecated
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED || connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTING || connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTING;
    }

    /**
     * Static method to check network availability
     *
     * @param context Context of the calling class
     */

    public static boolean getNetworkState(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Custom method to set the height of rating bar dynamically
     *
     * @param mContext  Context of the calling class
     * @param resid     Resource id of the image used
     * @param ratingBar Object of the rating bar
     */
    public static void setRatingBarHeight(Context mContext, int resid, RatingBar ratingBar) {
        Drawable starDrawable = mContext.getResources().getDrawable(resid);
        int height = starDrawable.getMinimumHeight();
        ViewGroup.LayoutParams params = ratingBar.getLayoutParams();
        params.height = height;
        ratingBar.setLayoutParams(params);
    }

    /**
     * Method to set text to a textview
     *
     * @param mTextView Textview in which to set text
     * @param text      The text to set in the widget
     */
    public static void setText(TextView mTextView, String text) {
        try {
            if (text != null)
                mTextView.setText(text.trim());
        } catch (Exception ignored) {
        }

    }

    /**
     * Method to set text to a textview
     *
     * @param mTextView Textview in which to set text
     * @param text      The text to set in the widget
     */
    public static void setText(TextView mTextView, String text, Typeface type) {
        try {
            if (text != null)
                mTextView.setText(text.trim());
            mTextView.setTypeface(type);
        } catch (Exception ignored) {
        }

    }

    /**
     * Method to set text to a textview
     *
     * @param mTextView Textview in which to set text
     * @param string
     * @param textResId The text id to set in the widget
     */
    public static void setText(TextView mTextView, String string, int textResId) {
        try {
            mTextView.setText(string);
            mTextView.setTypeface(null, textResId);
        } catch (Exception ignored) {
        }

    }

    /**
     * Method to call maps app and start navigation
     *
     * @param mContext   Context of the calling class
     * @param slatitude  Start destination latitude
     * @param slongitude Start destination longitude
     * @param dlatitude  Destination latitude
     * @param dlongitude Destination longitude
     */
    public static void startNavigation(Context mContext, String slatitude, String slongitude, String dlatitude,
                                       String dlongitude) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ditu.google.cn/maps?f=d&source=s_d"
                + "&saddr=" + slatitude + "," + slongitude + "&daddr=" + dlatitude + "," + dlongitude
                + "&hl=zh&t=m&dirflg=d"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK & Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        mContext.startActivity(intent);
    }

    /**
     * Static method to perform sms intent operation
     *
     * @param mContext    Context of the calling class
     * @param phoneNumber contact number
     */

    public static void sendSms(Context mContext, String phoneNumber) {
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber)));
    }

    public static Intent createSMSIntent(String number, String body) {
        Uri uri = Uri.parse("smsto:" + number);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
//        it.putExtra("exit_on_sent", false);
        it.putExtra("sms_body", body);
        return it;
    }

    /**
     * Static method to perform email intent operation
     *
     * @param mContext Context of the calling class
     * @param email    Email address to send to
     */
    public static void sendEmail(Context mContext, String email) {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
        mContext.startActivity(Intent.createChooser(emailIntent, "Send Email..."));
    }

    /**
     * Method to check email validation
     *
     * @param text      First object of edittext
     * @param context   Context of the calling activity
     * @param messageId Resource id of the string to show if both doesn't match
     * @return status of the matching
     */
    public static boolean validateEmailFields(String text, Context context, int messageId) {

        String expression = EMAIL_REGEX;
        Pattern p = Pattern.compile(expression, Pattern.CASE_INSENSITIVE); // pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;

        Matcher m = p.matcher(text);
        if (m.matches() && !text.isEmpty()) {
            return true;
        } else {
            showSnackBar((Activity) context, context.getResources().getString(messageId));
            return false;
        }
    }

    /**
     * Method to check email validation
     *
     * @param text      First object of edittext
     * @param context   Context of the calling activity
     * @param messageId Resource id of the string to show if both doesn't match
     * @return status of the matching
     */
    public static boolean validateUsernamaeFields(String text, Context context, int messageId) {

        String expression = NAME_REGEX;
        Pattern p = Pattern.compile(expression, Pattern.CASE_INSENSITIVE); // pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;

        Matcher m = p.matcher(text);
        if (m.matches() && !text.isEmpty()) {
            return true;
        } else {
            showSnackBar((Activity) context, context.getResources().getString(messageId));
            return false;
        }
    }


    /**
     * Method to check email validation
     *
     * @param editText  First object of edittext
     * @param context   Context of the calling activity
     * @param messageId Resource id of the string to show if both doesn't match
     * @return status of the matching
     */
    public static boolean validateMobileRegex(String editText, Context context, int messageId) {

        String expression = PHONE_REGEX;
        Pattern p = Pattern.compile(expression, Pattern.CASE_INSENSITIVE); // pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;

        Matcher m = p.matcher(editText);
        if (m.matches() && !editText.isEmpty()) {
            return true;
        } else {
            showSnackBar((Activity) context, context.getResources().getString(messageId));
            return false;
        }
    }

    /**
     * Method to check email validation
     *
     * @param editText  First object of edittext
     * @param context   Context of the calling activity
     * @param messageId Resource id of the string to show if both doesn't match
     * @return status of the matching
     */
    public static boolean validateMinSixRegex(String editText, Context context, int messageId) {

        String expression = MIN_SIX_REGEX;
        Pattern p = Pattern.compile(expression, Pattern.CASE_INSENSITIVE); // pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;

        Matcher m = p.matcher(editText);
        if (m.matches() && !editText.isEmpty()) {
            return true;
        } else {
            showSnackBar((Activity) context, context.getResources().getString(messageId));
            return false;
        }
    }

    /**
     * Method to check two edit text of same text
     *
     * @param editText  First object of edittext
     * @param context   Context of the calling activity
     * @param messageId Resource id of the string to show if both doesn't match
     * @return status of the matching
     */
    public static boolean validateInputFields(EditText editText, Context context, int messageId) {
        // boolean status = false;
        if (editText.getText() != null && isFieldEmpty(editText.getText().toString().trim())) {
            showAlertDialog(context, context.getResources().getString(messageId));
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method to check two edit text of same text
     *
     * @param text      Text to validate
     * @param context   Context of the calling activity
     * @param messageId Resource id of the string to show if both doesn't match
     * @return status of the matching
     */
    public static boolean validateInputFields(String text, Context context, int messageId) {
        if (text != null && isFieldEmpty(text)) {
            showSnackBar((Activity) context, context.getResources().getString(messageId));
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method to check two edittext of same text
     *
     * @param password     First object of edittext
     * @param confPassword Second object to match
     * @return status of the matching
     */
    public static boolean validatePasswordSameFields(String password, String confPassword,
                                                     Context context, int msgId) {
        boolean status;
        status = password.equals(confPassword);
        if (!status)
            showSnackBar((Activity) context, context.getResources().getString(msgId));
        return status;
    }

    /**
     * Static method to show alert dialog
     *
     * @param mContext Context of the calling class
     * @param text     Text to show in toast
     */
    public static void showAlertDialog(Context mContext, String text) {
        if (text == null)
            text = "";
        mAlertDialog = new AlertDialog.Builder(mContext).setMessage(text)
                .setTitle(mContext.getString(R.string.app_name)).setIcon(R.mipmap.ic_launcher).setCancelable(false)
                .setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog.dismiss();
                    }
                }).create();

        mAlertDialog.show();
        mAlertDialog.show();
        Button button = mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
    }

    /**
     * Static method to show alert dialog
     *
     * @param mContext Context of the calling class
     * @param text     Text to show in toast
     */
    public static void showalertdialog(Context mContext, String title, String text, String positiveButton) {
        if (text == null)
            text = "";
        mAlertDialog = new AlertDialog.Builder(mContext).setMessage(text)
                .setTitle(title).setIcon(R.mipmap.ic_launcher).setCancelable(false)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog.dismiss();
                    }
                }).create();

        mAlertDialog.show();
        mAlertDialog.show();
        Button button = mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
    }

    /**
     * Method for checking empty input fields
     *
     * @param field Field to check
     * @return true or false if field is empty
     */
    public static boolean isFieldEmpty(String field) {
        return field.trim().length() <= 0;
    }

    /**
     * General Method to generate Hash-key for facebook app.
     */
    public static void generateFBKeyHash(Context mContext) {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo("com.rahul.udacity.cs2",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to return state name from lat long
     *
     * @param mContext  Context of the calling class
     * @param latitude  user current latitude
     * @param longitude user current longitude
     * @return State of the location provided
     * @throws IOException
     */
    public static String getStateLocationFromLocation(Context mContext, double latitude, double longitude)

            throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(mContext, Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        return state;
    }

    /**
     * Method to return city, state name from lat long
     *
     * @param mContext  Context of the calling class
     * @param latitude  user current latitude
     * @param longitude user current longitude
     * @return State of the location provided
     * @throws IOException
     */
    public static String getCityStateLocationFromLocation(Context mContext, double latitude, double longitude)
            throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(mContext, Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        return city + ", " + state;
    }

    /**
     * Method to show keyboard
     *
     * @param context  Context of the calling activity
     * @param editText Edittext which will receive focus
     */
    public static void showKeyboard(Context context, EditText editText) {
        showKeyboard(context);
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
            imm.showSoftInput(((Activity) context).getCurrentFocus(), InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to show keyboard
     *
     * @param context Context of the calling activity
     */
    public static void showKeyboard(Context context) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(((Activity) context).getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Method to hide keyboard
     *
     * @param mContext Context of the calling class
     */
    public static void hideKeyboard(Context mContext) {
        try {
            InputMethodManager inputManager = (InputMethodManager) mContext
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(((Activity) mContext).getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }

    /**
     * Method to hide keyboard on view focus
     *
     * @param context    Context of the calling class
     * @param myEditText focussed view
     */
    public static void hideKeyboard(Context context, View myEditText) {
        hideKeyboard(context);
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }

    public static String getUtcDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        return formatter.format(calendar.getTime());
    }

    public static String getUtcDate(String date, String dateFormat) {

        //Date will return local time in Java

        //creating DateFormat for converting time from local timezone to GMT
        SimpleDateFormat converter = new SimpleDateFormat(dateFormat);

        //getting GMT timezone, you can get any timezone e.g. UTC
        converter.setTimeZone(TimeZone.getTimeZone("UTC"));

        System.out.println("local time : " + date);
        System.out.println("time in GMT : " + converter.format(date));

        return converter.format(date);
    }

    public static String getLocalDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        String dateString = formatter.format(new Date(milliSeconds));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        calendar.add(Calendar.MILLISECOND, TimeZone.getDefault().getOffset(calendar.getTimeInMillis()));
//        return formatter.format(calendar.getTime());
        return dateString;
    }

    public static String convertLocalTimeToUTC(String p_localDateTime, String dateFormat) throws Exception {

        String dateFormateInUTC = "";
        Date localDate = null;
        SimpleDateFormat formatter;
        SimpleDateFormat parser;

        //create a new Date object using the timezone of the specified city
        parser = new SimpleDateFormat(dateFormat);
        parser.setTimeZone(TimeZone.getDefault());
        localDate = parser.parse(p_localDateTime);

        formatter = new SimpleDateFormat(dateFormat);

        //Convert the date from the local timezone to UTC timezone
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateFormateInUTC = formatter.format(localDate);

        return dateFormateInUTC;
    }

    /**
     * Method to return the time in ago format
     *
     * @param timeString String representation of time
     * @param ctx        Context on calling class
     * @param dateFormat Source date format
     * @return Time representation in ago format
     */
    public static String getTimeAgo(String timeString, Context ctx, String dateFormat) {

        SimpleDateFormat sourceFormat = new SimpleDateFormat(dateFormat);
        sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date;
        String timeAgo;
        try {
            date = sourceFormat.parse(timeString);

            TimeZone tz = TimeZone.getDefault();
            SimpleDateFormat destFormat = new SimpleDateFormat(dateFormat);
            destFormat.setTimeZone(tz);

            String result = destFormat.format(date);
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            Date startDate = formatter.parse(result);

            long time = startDate.getTime();

            Date curDate = currentDate();
            long now = curDate.getTime();
            /*if (time >= now || time <= 0) {
                timeAgo = ctx.getResources().getString(R.string.date_util_term_less) + " " + ctx.getResources().getString(R.string.date_util_term_a) + " " + ctx.getResources().getString(R.string.date_util_unit_minute);
			}*/

            int dim = getTimeDistanceInMinutes(time);

            if (dim == 0 || dim == 1) {
                return /*timeAgo =  "1 " + ctx.getResources().getString(R.string.date_util_unit_minute)*/"now";
            } else if (dim >= 2 && dim <= 44) {
                timeAgo = dim + " " + ctx.getResources().getString(R.string.date_util_unit_minutes);
            } else if (dim >= 45 && dim <= 89) {
                timeAgo = "1 " + ctx.getResources().getString(R.string.date_util_unit_hour);
            } else if (dim >= 90 && dim <= 1439) {

                if ((Math.round(dim / 60)) == 1)
                    timeAgo = (Math.round(dim / 60)) + " "
                            + ctx.getResources().getString(R.string.date_util_unit_hour);
                else
                    timeAgo = (Math.round(dim / 60)) + " "
                            + ctx.getResources().getString(R.string.date_util_unit_hours);
            } else if (dim >= 1440 && dim <= 2519) {
                timeAgo = "1 " + ctx.getResources().getString(R.string.date_util_unit_day);
            } else if (dim >= 2520 && dim <= 43199) {
                timeAgo = (Math.round(dim / 1440)) + " "
                        + ctx.getResources().getString(R.string.date_util_unit_days);
            } else if (dim >= 43200 && dim <= 86399) {
                timeAgo = "1 " + ctx.getResources().getString(R.string.date_util_unit_month);
            } else if (dim >= 86400 && dim <= 525599) {
                timeAgo = (Math.round(dim / 43200)) + " "
                        + ctx.getResources().getString(R.string.date_util_unit_months);
            } else if (dim >= 525600 && dim <= 655199) {
                timeAgo = "1 "
                        + ctx.getResources().getString(R.string.date_util_unit_year);
            } else if (dim >= 655200 && dim <= 1051199) {
                timeAgo = "2 "
                        + ctx.getResources()
                        .getString(R.string.date_util_unit_year);
            } else {
                timeAgo = (Math.round(dim / 525600))
                        + " "
                        + ctx.getResources().getString(
                        R.string.date_util_unit_years);
            }
        } catch (Exception e) {
            timeAgo = timeString;
            e.printStackTrace();
        }
        return /*ctx.getResources().getString(R.string.date_util_prefix_about) + " " + */timeAgo
                + " " + ctx.getResources().getString(R.string.date_util_suffix);
    }

    /**
     * Method to return the difference in time
     *
     * @param time Time to get difference from current
     * @return Difference in time
     */
    private static int getTimeDistanceInMinutes(long time) {
        long timeDistance = currentDate().getTime() - time;
        return Math.round((Math.abs(timeDistance) / 1000) / 60);
    }

    /**
     * Returns an instance of calender
     *
     * @return an instance of calender
     */
    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        return calendar.getTime();
    }

    /**
     * Method to create a file in file system
     *
     * @param mContext Context of the calling class
     * @return Uri of the created file
     * @throws IOException Throws IO Exception
     */
    public static Uri createImageFile(Context mContext) throws IOException {

        File image;

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            image = File.createTempFile(imageFileName, /* prefix */
                    ".jpg", /* suffix */
                    storageDir /* directory */
            );

        } else {

            File storageDir = mContext.getFilesDir();
            image = File.createTempFile(imageFileName, /* prefix */
                    ".jpg", /* suffix */
                    storageDir /* directory */
            );

        }

        return Uri.fromFile(image);
    }

    /**
     * Remove the outer dialog padding so that it takes full screen width
     *
     * @param dialog Instance of the dialog
     */
    public static void removeDialogBounds(Dialog dialog) {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    /**
     * Remove the outer dialog padding so that it takes full screen width
     *
     * @param mActivity
     * @param dialog    Instance of the dialog
     */
    public static void removeDialogBounds2(Activity mActivity, Dialog dialog) {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = mActivity.getResources().getDisplayMetrics().widthPixels;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }


    /**
     * Method to convert stream to string
     *
     * @param inputStream Input Stream to convert
     */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    /**
     * Method to clear notification in status bar
     *
     * @param context Context of the calling class
     */

    public static void clearNotificationStatus(Context context) {
        try {
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancelAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to get an instance of material dialog with custom text message
     *
     * @param mContext   Context of the calling class
     * @param dialogText Text to set on dialog
     */

    public static MaterialProgressDialog getProgressDialogInstanceWithText(Context mContext,
                                                                           String dialogText) {
        MaterialProgressDialog mProgressDialog = new MaterialProgressDialog(mContext,
                dialogText);
        mProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mProgressDialog.setCancelable(false);
        return mProgressDialog;
    }

    /**
     * Method to copy database from internal memory to sd card
     *
     * @param mContext     Context of the calling class
     * @param databaseName Name of the database file
     */
    public static void exportDatabase(Context mContext, String databaseName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + mContext.getPackageName() + "//databases//" + databaseName + "";
                String backupDBPath = "qatarcool_backup.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to convert time in milliseconds to provided format
     *
     * @param timeInMillis Time in milliseconds
     * @param dateFormat   Target date format to convert into
     */

    public static String convertMillisToDateForChat(String timeInMillis, String dateFormat) {

        try {
            // Create a DateFormatter object for displaying date in specified
            // format.
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

            // Create a calendar object that will convert the date and time value in
            // milliseconds to date.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(timeInMillis));
            return formatter.format(calendar.getTime());
//            return parseDateTimeUtc(formattedUtcDate, Constants.DATE_FORMAT4, Constants.DATE_FORMAT4);
        } catch (Exception e) {
            e.printStackTrace();
            return timeInMillis;
        }
    }


    /**
     * A method to find height of the status bar
     */
    public static int getStatusBarHeight(Context mContext) {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * Method to check empty edittext
     *
     * @param editText Edittext to check
     * @return true if edittext contains text otherwise false
     */
    public static boolean validateEmptyEditText(EditText... editText) {
        for (int i = 0; i < editText.length; i++) {
            if (editText[i].getText().toString().isEmpty()) {
                return true;
            }
        }
        return false;

    }

    /**
     * Method to check empty edittext
     *
     * @param editText Edittext to check
     * @return true if edittext contains text otherwise false
     */
    public static boolean validateEmptyEditText(EditText editText, boolean isMandatory) {
        return editText.getText().toString().isEmpty() && isMandatory;

    }

    /**
     * Method to check empty edittext
     *
     * @param editText Edittext to check
     * @return true if edittext contains text otherwise false
     */
    public static boolean validateEmptyEditText(EditText editText, String isMandatory) {
        return editText.getText().toString().isEmpty() && isMandatory.equals("1");

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    /**
     * Method to parse date from server
     *
     * @param date         Date from the server
     * @param sourceFormat KFormatter of the date from server
     * @param targetFormat Target format in which to return the date
     * @return Formatted date
     */
    public static String parseDateTimeUtc(String date, String sourceFormat, String targetFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(sourceFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date strDate = new Date();

        try {
            strDate = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf2 = new SimpleDateFormat(targetFormat);
        sdf2.setTimeZone(TimeZone.getDefault());
        return sdf2.format(strDate);
    }

    private static void setTimeOnTextView(TextView view, int hourOfDay, int minute, TextView amPmTv) {
        String am_pm = "";

        Calendar datetime = Calendar.getInstance();
        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        datetime.set(Calendar.MINUTE, minute);

        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";

        String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";
        String startMinute = (datetime.get(Calendar.MINUTE) < 10 ? ("0" + datetime.get(Calendar.MINUTE)) : datetime.get(Calendar.MINUTE) + "");

        view.setText(strHrsToShow + ":" + startMinute);
        amPmTv.setText(am_pm);
    }

    public static boolean checkDateTimeFormatValid(String date, String sourceFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(sourceFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        boolean isValid = false;
        try {
            Date strDate = sdf.parse(date);
            isValid = true;
        } catch (Exception e) {
            e.printStackTrace();
            isValid = false;
        }

        return isValid;
    }

    /**
     * Return a unique identifier
     *
     * @param mFromUserId
     * @param mToUserId
     * @return
     */
    public static String getChatHistoryPrefsKey(String mFromUserId, String mToUserId) {
        return "prefs_chat_history_" + mFromUserId + "_" + mToUserId;
    }

    /**
     * Remove the outer dialog padding so that it takes full screen width
     *
     * @param dialog Instance of the dialog
     */
    public static void modifyDialogBounds2(Dialog dialog) {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.width = (int) (dialog.getContext().getResources().getDisplayMetrics().widthPixels * 0.9);
//        lp.height = (int) (dialog.getContext().getResources().getDisplayMetrics().heightPixels * 0.55);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    public static void showToastShort(Context activity, int messageId) {
        Toast.makeText(activity, messageId, Toast.LENGTH_SHORT).show();
    }

    public static void showAlertDialog(Context mContext, String text, final OkCancelCallback okCancelCallback) {
        if (text == null)
            text = "";
        mAlertDialog = new AlertDialog.Builder(mContext).setMessage(text)
                .setCancelable(false)
                .setTitle(mContext.getString(R.string.app_name)).setIcon(R.mipmap.ic_launcher).setCancelable(false)
                .setPositiveButton(mContext.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog.dismiss();
                        if (okCancelCallback != null)
                            okCancelCallback.onOkClicked();
                    }
                }).setNegativeButton(mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog.dismiss();
                        if (okCancelCallback != null)
                            okCancelCallback.onCancelClicked();
                    }
                }).create();
        mAlertDialog.show();
        Button okButton = mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button cancelButton = mAlertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        okButton.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        cancelButton.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
    }

    public static void showAlertDialogWithOk(Context mContext, String text, final OkCancelCallback okCancelCallback) {
        if (text == null)
            text = "";
        mAlertDialog = new AlertDialog.Builder(mContext).setMessage(text)
                .setCancelable(false)
                .setTitle(mContext.getString(R.string.app_name)).setIcon(R.mipmap.ic_launcher).setCancelable(false)
                .setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlertDialog.dismiss();
                        if (okCancelCallback != null)
                            okCancelCallback.onOkClicked();
                    }
                }).create();
        mAlertDialog.show();
        Button okButton = mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button cancelButton = mAlertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        okButton.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        cancelButton.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
    }

    public static String formatValue(double value) {
        try {
            int power;
            String suffix = " kmbt";
            String formattedNumber = "";

            NumberFormat formatter = new DecimalFormat("#,###.#");
            power = (int) StrictMath.log10(value);
            value = value / (Math.pow(10, (power / 3) * 3));
            formattedNumber = formatter.format(value);
            formattedNumber = formattedNumber + suffix.charAt(power / 3);
            return formattedNumber.length() > 4 ? formattedNumber.replaceAll("\\.[0-9]+", "") : formattedNumber;
        } catch (Exception e) {
            return "";
        }
    }

    public static String KFormatter(double number) {
        return formatValue(number);
        /*String[] suffix = new String[]{"k", "m", "b", "t"};
        int size = (number != 0) ? (int) Math.log10(number) : 0;
        if (size >= 3) {
            while (size % 3 != 0) {
                size = size - 1;
            }
        }
        double notation = Math.pow(10, size);
        String result = (size >= 3) ? +(Math.round((number / notation) * 100) / 100.0d) + suffix[(size / 3) - 1] : +number + "";
        return result;*/
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

    public static void showSnackBar(Activity mActivity, String msgStr) {
        CustomToast.getInstance(mActivity).showToast(msgStr);
//        Snackbar.make(((ViewGroup) mActivity.findViewById(android.R.id.content)).getChildAt(0), msgStr, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnackBar(Activity mActivity, int msgRes) {
        CustomToast.getInstance(mActivity).showToast(msgRes);
//        Snackbar.make(((ViewGroup) mActivity.findViewById(android.R.id.content)).getChildAt(0), msgRes, Snackbar.LENGTH_LONG).show();
    }

    public static void addRotationAnimation(ImageView simpleDraweeView) {
        if (simpleDraweeView != null) {
            ObjectAnimator rotation = ObjectAnimator.ofFloat(simpleDraweeView, "rotation", 0, 359);
            rotation.setRepeatCount(ObjectAnimator.INFINITE);
            rotation.setRepeatMode(ObjectAnimator.RESTART);
            rotation.setInterpolator(new LinearInterpolator());

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(simpleDraweeView, "scaleX", 0.80f, 1.25f);
            scaleX.setRepeatMode(ValueAnimator.REVERSE);
            scaleX.setRepeatCount(ValueAnimator.INFINITE);

            ObjectAnimator scaleY = ObjectAnimator.ofFloat(simpleDraweeView, "scaleY", 0.80f, 1.25f);
            scaleY.setRepeatMode(ValueAnimator.REVERSE);
            scaleY.setRepeatCount(ValueAnimator.INFINITE);

            AnimatorSet scaleSet = new AnimatorSet();
            scaleSet.playTogether(scaleX, scaleY, rotation);
            scaleSet.setDuration(1000);
            scaleSet.start();
        }
    }

    public static void changeToArabic(Context context) {
        /*Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        Locale ar= new Locale("ar");
        conf.locale = ar;

        res.updateConfiguration(conf, dm);*/
    }


    public static int getSeparatorColor(Context context) {
        int colorArray[] = {context.getResources().getColor(R.color.blue_5dc9df), context.getResources().getColor(R.color.blue_71ccc6),
                context.getResources().getColor(R.color.green_cad46d), context.getResources().getColor(R.color.blue_247bb9),
                context.getResources().getColor(R.color.yellow_c8d473), context.getResources().getColor(R.color.yellow_fdd093),
                context.getResources().getColor(R.color.green_71af3d), context.getResources().getColor(R.color.orange_f49b45)};

        Random random = new Random();
        int i = random.nextInt(7);
        return colorArray[i];
    }
}

