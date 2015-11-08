package blueset.triangles.com.blueset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by mittu on 11/7/2015.
 */
public class CallStateReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {                                         // 2
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);                         // 3
        String msg = "Phone state changed to " + state;
        //CallStateHandlerService callStateHandlerService;
        //callStateHandlerService = new CallStateHandlerService("CALL_STATE_HANDLER");
        Intent blueIntent = new Intent(context,CallStateHandlerService.class);
        //callStateHandlerService.startService(intent);
        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {                                   // 4
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);  // 5
            msg += ". Incoming number is " + incomingNumber;

            blueIntent.putExtra("switchBluetoothToState", true);
            context.startService(blueIntent);
            // TODO This would be a good place to "Do something when the phone rings" <img src="http://androidlabs.org/wp-includes/images/smilies/icon_wink.gif" alt=";-)" class="wp-smiley">

        }
        else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state))
        {
            blueIntent.putExtra("switchBluetoothToState", false);
            context.startService(blueIntent);
        }
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}