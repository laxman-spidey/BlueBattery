package blueset.triangles.com.blueset;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String msg = "Phone state changed to " + state;
        String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        msg += ". Incoming number is " + incomingNumber;
        boolean isMusicActive = audioManager.isMusicActive();
        LogUtil.print("music active = " +isMusicActive);

        if(isMusicActive == false)
        {
            Intent blueIntent = new Intent(context, CallStateHandlerService.class);
            blueIntent.putExtra("switchBluetoothToState", true);
            context.startService(blueIntent);
        }
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

    }

}
