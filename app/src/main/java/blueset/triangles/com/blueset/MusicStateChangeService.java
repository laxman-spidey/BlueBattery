package blueset.triangles.com.blueset;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.telephony.TelephonyManager;

import java.lang.reflect.Constructor;

import blueset.triangles.com.blueset.util.ConstantUtil;
import blueset.triangles.com.blueset.util.LogUtil;

public class MusicStateChangeService extends IntentService {

    public MusicStateChangeService() {
        super(ConstantUtil.MUSIC_STATE);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        LogUtil.print("service started");
        if (intent != null) {
            if (intent.hasExtra(ConstantUtil.MUSIC_STATE)) {
                String previousState = getMusicStateFromSharedPref();
                LogUtil.print("previous state " + previousState);
                if (previousState.equals(ConstantUtil.MUSIC_STATE_STOP)) {
                    handleMusicIntent(intent);
                } else {
                    //total 10 sec
                    for (int i = 0; i <= 01; i++) {

                        try {
                            Thread.sleep(1000); //every 1 sec
                            LogUtil.print(i + "....");
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (getCallStateFromSharedPref().equals(ConstantUtil.CALL_STATE_CONNECTED))
                    {
                        setMusicState(ConstantUtil.MUSIC_STATE_PLAY);
                    }
                    else
                    {
                        handleMusicIntent(intent);
                    }
                }
            }
        }
    }

    protected void setMusicState(String state) {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(ConstantUtil.MUSIC_STATE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ConstantUtil.MUSIC_STATE, state);
        editor.commit();
    }

    protected void handleMusicIntent(Intent intent) {
        LogUtil.print("handling intent");
        if (intent.getBooleanExtra(ConstantUtil.INTENT_ACTION_PLAYING, false)) {
            setMusicState(ConstantUtil.MUSIC_STATE_PLAY);
        } else {
            setMusicState(ConstantUtil.MUSIC_STATE_STOP);
        }


    }

    private String getMusicStateFromSharedPref() {
        SharedPreferences sharedPref1 = getApplicationContext().getSharedPreferences(ConstantUtil.MUSIC_STATE_PREF, Context.MODE_PRIVATE);
        String state = sharedPref1.getString(ConstantUtil.MUSIC_STATE, ConstantUtil.MUSIC_STATE_NONE);
        LogUtil.print("music_state " + state);
        return state;

    }
    private String getCallStateFromSharedPref() {
        SharedPreferences sharedPref1 = getApplicationContext().getSharedPreferences(ConstantUtil.CALL_STATE_PREF, Context.MODE_PRIVATE);
        String state = sharedPref1.getString(ConstantUtil.CALL_STATE_STRING, ConstantUtil.CALL_STATE_NONE);
        LogUtil.print("Call_state" + state);
        return state;

    }
}

