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
        String state = sharedPref1.getString(ConstantUtil.MUSIC_STATE, ConstantUtil.MUSIC_STATE_NONE);
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
        return state;
    }
    public void setBluetoothState(boolean state)
    {
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences(ConstantUtil.BLUE_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(ConstantUtil.BLUETOOTH_STATE_PREF, state);
    }


}