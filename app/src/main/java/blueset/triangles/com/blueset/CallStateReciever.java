package blueset.triangles.com.blueset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import blueset.triangles.com.blueset.util.LogUtil;

/**
 * Created by mittu on 11/7/2015.
 */
public class CallStateReciever extends BroadcastReceiver {

    public static String ACTION_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    public static String ACTION_INCOMING_CALL = "android.intent.action.PHONE_STATE";
    public static String ACTION_MUSIC_STATE_CHANGE = "com.android.music.playstatechanged";

    @Override
    public void onReceive(Context context, Intent intent) {

        String broadcastAction = intent.getAction();
        LogUtil.print("broadcast eventbbb recieved " + intent.getAction());
        if(broadcastAction.equals(ACTION_MUSIC_STATE_CHANGE))
        {
            if(intent.hasExtra("playing")) {
                Intent mediaIntent = new Intent(context, MusicStateChangeService.class);
                mediaIntent.putExtra("MUSIC_STATE", true);
                mediaIntent.putExtra("playing",intent.getBooleanExtra("playing",false));
                context.startService(mediaIntent);
            }
        }
        else {
            Intent blueIntent = new Intent(context, CallStateHandlerService.class);
            blueIntent.putExtra("ACTION_CALL_STATE", broadcastAction);
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            blueIntent.putExtra(TelephonyManager.EXTRA_STATE, state);
            context.startService(blueIntent);
        }
        /*
        if(broadcastAction.equals(ACTION_OUTGOING_CALL))
        {
            if(getMusicState(context) == false)
            {
                Intent blueIntent = new Intent(context, CallStateHandlerService.class);
                blueIntent.putExtra("switchBluetoothToState", true);
                context.startService(blueIntent);
                return;
            }
        }

        if(broadcastAction.equals(ACTION_INCOMING_CALL))
        {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
                return;
            }
            if(getMusicState(context)== false) {
                Intent blueIntent = new Intent(context, CallStateHandlerService.class);
                boolean bluetoothState = false;

                if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {

                    bluetoothState = true;
                } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
                    bluetoothState = false;
                }
                blueIntent.putExtra("CALL_STATE", state);
                blueIntent.putExtra("switchBluetoothToState", bluetoothState);
                context.startService(blueIntent);
            }
        }

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String msg = "Phone state changed to " + state;
        String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        msg += ". Incoming number is " + incomingNumber;
        LogUtil.print(msg);
        */
    }
}