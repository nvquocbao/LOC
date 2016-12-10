package io.hackathon.santaclaus.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import io.hackathon.santaclaus.activity.LoginActivity;
import io.hackathon.santaclaus.model.User;

/**
 * Created by trinhnt on 2016/12/10.
 */

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "SantaClaus";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // User id
    public static final String KEY_ID = "id";
    // User name
    public static final String KEY_NAME = "name";
    // Email address
    public static final String KEY_EMAIL = "email";
    // Password
    public static final String KEY_PASSWORD = "password";
    // Birthday
    public static final String KEY_BIRTHDAY = "birthday";
    // Gender
    public static final String KEY_GENDER = "gender";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     *
     * @param user
     */
    public void createLoginSession(User user) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_ID, user.getId() + "");

        // Storing name in pref
        editor.putString(KEY_NAME, user.getName());

        // Storing email in pref
        editor.putString(KEY_EMAIL, user.getEmail());

        editor.putString(KEY_PASSWORD, user.getPassword());

        editor.putString(KEY_BIRTHDAY, user.getBirthday());

//        editor.putString(KEY_GENDER, Utils.getStringValue(user.getGender()));

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    /**
     * Get stored session data
     */
    public User getUserDetails() {
        if (null == pref.getString(KEY_ID, null)) {
            return null;
        }
        User user = new User();
        user.setId(Utils.getIntegerValue(pref.getString(KEY_ID, null)));
        user.setName(pref.getString(KEY_NAME, null));
        user.setEmail(pref.getString(KEY_EMAIL, null));
        user.setPassword(pref.getString(KEY_PASSWORD, null));
        user.setBirthday(pref.getString(KEY_BIRTHDAY, null));
//        user.setGender(Utils.getIntegerValue(pref.getString(KEY_GENDER, null)));
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to LoginActivity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Get Login State
     * @return
     */
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

}
