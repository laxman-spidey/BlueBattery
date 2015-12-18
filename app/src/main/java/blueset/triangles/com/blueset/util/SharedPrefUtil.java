package blueset.triangles.com.blueset.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mittu on 12/14/2015.
 */
public class SharedPrefUtil
{
    Context context;
    public SharedPrefUtil(Context context)
    {
        this.context = context;
    }
    public void setCallState(String callState)
    {
        LogUtil.print("setting call state to " + callState);
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences(ConstantUtil.CALL_STATE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ConstantUtil.CALL_STATE_STRING,callState);
        editor.commit();
    }
    public String getCallStateFromSharedPref() {
        SharedPreferences sharedPref1 = context.getApplicationContext().getSharedPreferences(ConstantUtil.CALL_STATE_PREF, Context.MODE_PRIVATE);
        String state = sharedPref1.getString(ConstantUtil.CALL_STATE_STRING, ConstantUtil.CALL_STATE_NONE);
        LogUtil.print("Call_state" + state);
        return state;
    }

    public String getMusicStateFromSharedPref()
    {
        SharedPreferences sharedPref1 = context.getApplicationContext().getSharedPreferences(ConstantUtil.MUSIC_STATE_PREF, Context.MODE_PRIVATE);
        String state = sharedPref1.getString(ConstantUtil.MUSIC_STATE, ConstantUtil.MUSIC_STATE_STOP);
        LogUtil.print("music_state " + state);
        return state;

    }
    public void setMusicState(String state) {
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences(ConstantUtil.MUSIC_STATE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ConstantUtil.MUSIC_STATE, state);
        editor.commit();
    }
    public boolean getBluetoothState()
    {
        SharedPreferences sharedPref1 = context.getApplicationContext().getSharedPreferences(ConstantUtil.BLUE_SHARED_PREF, Context.MODE_PRIVATE);
        boolean state = sharedPref1.getBoolean(ConstantUtil.BLUETOOTH_STATE_PREF, false);
        LogUtil.print("bluetooth previous state  = " + state);
        return state;
    }
    public void setBluetoothState(boolean state)
    {
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences(ConstantUtil.BLUE_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        LogUtil.print("setting previous state "+state);
        editor.putBoolean(ConstantUtil.BLUETOOTH_STATE_PREF, state);
        editor.commit();
    }
    public String getBluetoothSessionState()
    {
        SharedPreferences sharedPref1 = context.getApplicationContext().getSharedPreferences(ConstantUtil.BLUE_SHARED_PREF, Context.MODE_PRIVATE);
        String state = sharedPref1.getString(ConstantUtil.BLUEOOTH_SESSION, ConstantUtil.BLUEOOTH_SESSION_OFF);
        LogUtil.print("bluetooth session state  = " + state);
        return state;
    }
    public void setBluetoothSessionState(String state)
    {
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences(ConstantUtil.BLUE_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        LogUtil.print("setting bluetooth session state "+state);
        editor.putString(ConstantUtil.BLUEOOTH_SESSION, state);
        editor.commit();
    }
    public void setFirstTimeUse(boolean firstTime)
    {
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences(ConstantUtil.BLUE_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        LogUtil.print("first time usage"+firstTime);
        editor.putBoolean(ConstantUtil.FIRST_TIME_USE_PREF, firstTime);
        editor.commit();
    }
    public boolean getFirstTimeUse()
    {
        SharedPreferences sharedPref1 = context.getApplicationContext().getSharedPreferences(ConstantUtil.BLUE_SHARED_PREF, Context.MODE_PRIVATE);
        boolean state = sharedPref1.getBoolean(ConstantUtil.FIRST_TIME_USE_PREF, true);
        LogUtil.print("bluetooth session state  = " + state);
        return state;
    }
}
