package com.vnh.filmmarket;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HUYVINH on 09-Jan-17.
 */

public class PrefManager {
    SharedPreferences sf;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "films_market";
    private static final String IS_FIRST_TIME_TO_LAUNCH = "IsFirstTimeLaunh";

    public PrefManager(Context context) {
        sf = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sf.edit();
        this.context = context;
    }

    public void setIsFirstTimeToLaunch(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_TIME_TO_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeToLaunch(){
        return sf.getBoolean(IS_FIRST_TIME_TO_LAUNCH,true);
    }
}
