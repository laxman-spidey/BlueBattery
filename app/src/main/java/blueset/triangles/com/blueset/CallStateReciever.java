package blueset.triangles.com.blueset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import blueset.triangles.com.blueset.util.ConstantUtil;
import blueset.triangles.com.blueset.util.LogUtil;

/**
 * Created by mittu on 11/7/2015.
 */
public class CallStateReciever extends BroadcastReceiver {

    public static String ACTION_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    public static String ACTION_INCOMING_CALL = "android.intent.action.PHONE_STATE";

    @Override
    public void onReceive(Context context, Intent intent) {

        String broadcastAction = intent.getAction();
        LogUtil.print("broadcast event recieved " + intent.getAction());
        if(broadcastAction.equals(ConstantUtil.ACTION_MUSIC_STATE_CHANGE))
        {
            if(intent.hasExtra("playing")) {
                Intent mediaIntent = new Intent(context, MusicStateChangeService.class);
                mediaIntent.putExtra(ConstantUtil.MUSIC_STATE, true);
                mediaIntent.putExtra(ConstantUtil.INTENT_ACTION_PLAYING, intent.getBooleanExtra(ConstantUtil.INTENT_ACTION_PLAYING, false));
                context.startService(mediaIntent);
            }
        }
        else {
            Intent blueIntent = new Intent(context, CallStateHandlerService.class);
            blueIntent.putExtra(ConstantUtil.CUSTOM_ACTION_CALL_STATE, broadcastAction);
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            blueIntent.putExtra(TelephonyManager.EXTRA_STATE, state);
            context.startService(blueIntent);
        }
    }
}