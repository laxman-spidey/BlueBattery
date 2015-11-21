package blueset.triangles.com.blueset;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import blueset.triangles.com.blueset.util.LogUtil;

/**
 * Created by mittu on 11/7/2015.
 */
public class CallStateHandlerService extends IntentService {

    public static String ACTION_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    public static String ACTION_INCOMING_CALL = "android.intent.action.PHONE_STATE";

    public CallStateHandlerService() {
        super("CALL_STATE_HANDLER");
    }
    public CallStateHandlerService(String name) {
        super(name);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtil.print("service started");
        String broadcastCallState = intent.getStringExtra("ACTION_CALL_STATE");
        if(broadcastCallState.equals(ACTION_OUTGOING_CALL))
        {
            LogUtil.print("outgoing call");
            sendBluetoothAction(true);
        }
        else if(broadcastCallState.equals(ACTION_INCOMING_CALL))
        {
            String callState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if(callState.equals(TelephonyManager.EXTRA_STATE_RINGING))
            {
                LogUtil.print("Incoming call");
                sendBluetoothAction(true);
            }
            else if(callState.equals(TelephonyManager.EXTRA_STATE_IDLE))
            {
                LogUtil.print("call disconnected");
                sendBluetoothAction(false);
            }
            else {
                LogUtil.print("others");
                return;
            }
        }

        if(intent.hasExtra("MUSIC_STATE")) {
            //handleMusicEvents(intent);
        }
        //LogUtil.print("handling Intent");

    }
    /*
    protected void handleMusicEvents(Intent intent)
    {

            boolean musicState = intent.getBooleanExtra("MUSIC_STATE", false);
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MUSIC_STATE_PREF", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            if(intent.getBooleanExtra("playing", false))
            {
                editor.putBoolean("MUSIC_STATE", true);
            } else {
                LogUtil.print("not playing");
                editor.putBoolean("MUSIC_STATE", false);
            }
            editor.commit();
            SharedPreferences sharedPref1 = getApplicationContext().getSharedPreferences("MUSIC_STATE_PREF", Context.MODE_PRIVATE);
            LogUtil.print("music_state " + sharedPref1.getBoolean("MUSIC_STATE", false));

    }
    */
    private boolean getMusicState(Context context)
    {
        boolean musicState = false;
        SharedPreferences sharedPref2 = context.getApplicationContext().getSharedPreferences("MUSIC_STATE_PREF", Context.MODE_PRIVATE);
        if(sharedPref2.contains("MUSIC_STATE")) {
            LogUtil.print("Getting Music state");
            musicState = sharedPref2.getBoolean("MUSIC_STATE", false);
        }
        LogUtil.print("music_state " + musicState);
        return musicState;
    }
    private void sendBluetoothAction(boolean bluetoothState)
    {
        LogUtil.print("turning bluetooth " + bluetoothState);
        BluetoothController bluetoothController = new BluetoothController();
        bluetoothController.switchBluetooth(bluetoothState);
    }
    private boolean getMusicStateFromSharedPref()
    {
        SharedPreferences sharedPref1 = getApplicationContext().getSharedPreferences("MUSIC_STATE_PREF", Context.MODE_PRIVATE);
        boolean state = sharedPref1.getBoolean("MUSIC_STATE", false);
        LogUtil.print("music_state " + state);
        return state;

    }
}