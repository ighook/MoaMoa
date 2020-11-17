package com.moamoa;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_ID= "id";
    static final String PREF_PW= "pw";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserInfo(Context ctx, String userInfo)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_ID, userInfo);
        editor.putString(PREF_PW, userInfo);
        editor.apply();
    }

    public static String getUserInfo(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_ID, "");
    }
}
