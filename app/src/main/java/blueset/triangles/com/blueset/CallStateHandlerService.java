package blueset.triangles.com.blueset;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import blueset.triangles.com.blueset.util.ConstantUtil;
import blueset.triangles.com.blueset.util.LogUtil;

/**
 * Created by mittu on 11/7/2015.
 */
public class CallStateHandlerService extends IntentService {

    public static String ACTION_OUTGOING_CALL = ConstantUtil.ACTION_OUTGOING_CALL;
    public static String ACTION_INCOMING_CALL = ConstantUtil.ACTION_INCOMING_CALL;

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
            setCallState(ConstantUtil.CALL_STATE_CONNECTED);
            sendBluetoothAction(true);
        }
        else if(broadcastCallState.equals(ACTION_INCOMING_CALL))
        {
            String callState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if(callState.equals(TelephonyManager.EXTRA_STATE_RINGING))
            {
                setCallState(ConstantUtil.CALL_STATE_CONNECTED);
                LogUtil.print("Incoming call");
                sendBluetoothAction(true);
            }
            else if(callState.equals(TelephonyManager.EXTRA_STATE_IDLE))
            {
                setCallState(ConstantUtil.CALL_STATE_DISCONNECTED);
                LogUtil.print("call disconnected");
                if(getMusicStateFromSharedPref().equals(ConstantUtil.MUSIC_STATE_STOP)) {
                    sendBluetoothAction(false);
                }
            }
            else {
                LogUtil.print("others");
                return;
            }
        }
    }

    private void setCallState(String callState)
    {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(ConstantUtil.CALL_STATE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ConstantUtil.CALL_STATE_STRING,callState);
        editor.commit();
    }
    private void sendBluetoothAction(boolean bluetoothState)
    {
        LogUtil.print("turning bluetooth " + bluetoothState);
        BluetoothController bluetoothController = new BluetoothController();
        bluetoothController.switchBluetooth(bluetoothState);
    }
    private String getMusicStateFromSharedPref()
    {
        SharedPreferences sharedPref1 = getApplicationContext().getSharedPreferences(ConstantUtil.MUSIC_STATE_PREF, Context.MODE_PRIVATE);
        String state = sharedPref1.getString(ConstantUtil.MUSIC_STATE, ConstantUtil.MUSIC_STATE_NONE);
        LogUtil.print("music_state " + state);
        return state;

    }
}