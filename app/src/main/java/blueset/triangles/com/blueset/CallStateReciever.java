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
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.print("broadcast event reccieved " + intent.getAction());
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String msg = "Phone state changed to " + state;
        String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        msg += ". Incoming number is " + incomingNumber;
        SharedPreferences sharedPref2 = context.getSharedPreferences("MUSIC_STATE_PREF", Context.MODE_PRIVATE);
        boolean musicState = sharedPref2.getBoolean("MUSIC_STATE", false);
        if(!musicState) {

            Intent blueIntent = new Intent(context, CallStateHandlerService.class);
            if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
                blueIntent.putExtra("switchBluetoothToState", true);
                context.startService(blueIntent);
            } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
                blueIntent.putExtra("switchBluetoothToState", false);
                context.startService(blueIntent);
            }
        }
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        if(intent.hasExtra("playing")) {
            SharedPreferences sharedPref = context.getSharedPreferences("MUSIC_STATE_PREF", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            if(intent.getBooleanExtra("playing", false)) {
                editor.putBoolean("MUSIC_STATE", true);
            } else {
                LogUtil.print("not playing");
                editor.putBoolean("MUSIC_STATE", false);
            }
            editor.commit();
            SharedPreferences sharedPref1 = context.getSharedPreferences("MUSIC_STATE_PREF", Context.MODE_PRIVATE);
            LogUtil.print("music_state " + sharedPref1.getBoolean("MUSIC_STATE", false));

        }
    }
}