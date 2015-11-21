package blueset.triangles.com.blueset;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.telephony.TelephonyManager;

import blueset.triangles.com.blueset.util.LogUtil;

public class MusicStateChangeService extends IntentService {

    public MusicStateChangeService() {
        super("MUSIC_STATE");
    }
    public boolean inSameService = false;

    @Override
    protected void onHandleIntent(final Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if(intent.hasExtra("MUSIC_STATE")) {
                boolean isPlaying = intent.getBooleanExtra("playing", false);
                boolean previousState = getMusicStateFromSharedPref();
                if(previousState == false)
                {
                    handleMusicIntent(intent);
                }
                else {
                    Handler mHandle = new Handler();
                    LogUtil.print("waiting for 1 seconds before turning off bluetooth");
                    final boolean b = mHandle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LogUtil.print("After 1 second");
                            if (isMusicTurnedOffBySystem == false) {
                                handleMusicIntent(intent);
                            } else {
                                LogUtil.print("music turned off by system because of a call,  ignoring the state");
                            }
                        }
                    }, 1000);
                }
            }
        }

    }
    protected void handleMusicIntent(Intent intent)
    {
        LogUtil.print("After 1 second");
        boolean musicState = intent.getBooleanExtra("MUSIC_STATE", false);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MUSIC_STATE_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        LogUtil.print("music = " + intent.getBooleanExtra("playing", true));
        if(intent.getBooleanExtra("playing", false))
        {
            editor.putBoolean("MUSIC_STATE", true);
        } else {
            LogUtil.print("not playing");
            editor.putBoolean("MUSIC_STATE", false);
        }

        editor.commit();

    }

    private boolean getMusicStateFromSharedPref()
    {
        SharedPreferences sharedPref1 = getApplicationContext().getSharedPreferences("MUSIC_STATE_PREF", Context.MODE_PRIVATE);
        boolean state = sharedPref1.getBoolean("MUSIC_STATE", false);
        LogUtil.print("music_state " + state);
        return state;

    }
    private BroadcastReceiver callStateReciever;
    private boolean isMusicTurnedOffBySystem = false;

    private static String ACTION_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    private static String ACTION_INCOMING_CALL = "android.intent.action.PHONE_STATE";

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.print("on create()");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_INCOMING_CALL);
        intentFilter.addAction(ACTION_OUTGOING_CALL);
        callStateReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String callState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                if (callState.equals(TelephonyManager.EXTRA_STATE_IDLE) || callState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    LogUtil.print("isMusicTurnedOffBySystem = false");
                    return;
                }
                else
                {
                    LogUtil.print("isMusicTurnedOffBySystem = true");
                    isMusicTurnedOffBySystem = true;
                }
            }
        };
        this.registerReceiver(callStateReciever,intentFilter);
    }

    @Override
    public void onDestroy() {
        LogUtil.print("on destroy()");
        this.unregisterReceiver(callStateReciever);
        super.onDestroy();
    }
}
