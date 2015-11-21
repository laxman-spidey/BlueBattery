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
 * Created by mittu on 11/11/2015.
 */
public class OutgoingCallStateReciever extends BroadcastReceiver {


    public void onReceive(Context context, Intent intent) {
        LogUtil.print("broadcast event reccieved " + intent.getAction());
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String msg = "Phone state changed to " + state;
        String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        msg += ". Incoming number is " + incomingNumber;
        if(getMusicState(context) == false)
        {
            Intent blueIntent = new Intent(context, CallStateHandlerService.class);
            blueIntent.putExtra("switchBluetoothToState", true);
            context.startService(blueIntent);
        }
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
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



}
