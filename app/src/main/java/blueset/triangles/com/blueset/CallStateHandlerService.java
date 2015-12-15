package blueset.triangles.com.blueset;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import blueset.triangles.com.blueset.util.ConstantUtil;
import blueset.triangles.com.blueset.util.LogUtil;
import blueset.triangles.com.blueset.util.SharedPrefUtil;

/**
 * Created by mittu on 11/7/2015.
 */
public class CallStateHandlerService extends IntentService {

    public static String ACTION_OUTGOING_CALL = ConstantUtil.ACTION_OUTGOING_CALL;
    public static String ACTION_INCOMING_CALL = ConstantUtil.ACTION_INCOMING_CALL;

    SharedPrefUtil sharedPrefUtil;
    public CallStateHandlerService() {
        super(ConstantUtil.CUSTOM_ACTION_CALL_STATE);
    }
    public CallStateHandlerService(String name) {
        super(name);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtil.print("service started");
        sharedPrefUtil = new SharedPrefUtil(getApplicationContext());
        String broadcastCallState = intent.getStringExtra("ACTION_CALL_STATE");
        if(broadcastCallState.equals(ACTION_OUTGOING_CALL))
        {
            LogUtil.print("outgoing call");
            sharedPrefUtil.setCallState(ConstantUtil.CALL_STATE_OUTGOING_STARTED);
            //sendBluetoothAction(true);
        }
        else if(broadcastCallState.equals(ACTION_INCOMING_CALL))
        {
            String callState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if(callState.equals(TelephonyManager.EXTRA_STATE_RINGING))
            {
                sharedPrefUtil.setCallState(ConstantUtil.CALL_STATE_CONNECTED);
                LogUtil.print("Incoming call");
                if(sharedPrefUtil.getBluetoothSessionState().equals(ConstantUtil.BLUEOOTH_SESSION_OFF))
                {
                    sharedPrefUtil.setBluetoothSessionState(ConstantUtil.BLUEOOTH_SESSION_ON_AUTO);
                }
                sendBluetoothAction(true);
            }
            else if(callState.equals(TelephonyManager.EXTRA_STATE_IDLE))
            {
                boolean previousState1 = sharedPrefUtil.getBluetoothState();
                sharedPrefUtil.setCallState(ConstantUtil.CALL_STATE_DISCONNECTED);
                LogUtil.print("call disconnected");
                String musicState = sharedPrefUtil.getMusicStateFromSharedPref();
                if(sharedPrefUtil.getBluetoothSessionState().equals(ConstantUtil.BLUEOOTH_SESSION_ON_MANUALLY))
                {
                    LogUtil.print("bluetooth turned on manually. So, keeping it turned on");
                    return;
                }
                if((musicState.equals(ConstantUtil.MUSIC_STATE_STOP) || musicState.equals(ConstantUtil.MUSIC_STATE_NONE))) {
                    sendBluetoothAction(false);
                }
            }
            else if(callState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                if(sharedPrefUtil.getCallStateFromSharedPref().equals(ConstantUtil.CALL_STATE_OUTGOING_STARTED))
                {
                    LogUtil.print("ne mokam.. raavey");
                    sharedPrefUtil.setCallState(ConstantUtil.CALL_STATE_CONNECTED);
                    if(sharedPrefUtil.getBluetoothSessionState().equals(ConstantUtil.BLUEOOTH_SESSION_OFF))
                    {
                        sharedPrefUtil.setBluetoothSessionState(ConstantUtil.BLUEOOTH_SESSION_ON_AUTO);
                    }
                    sendBluetoothAction(true);
                }
                LogUtil.print("others");
                return;
            }
            else
            {
                LogUtil.print("callstate "+ callState);
            }
        }
    }


    private void sendBluetoothAction(boolean bluetoothState)
    {
        LogUtil.print("turning bluetooth " + bluetoothState);
        BluetoothController bluetoothController = new BluetoothController();
        bluetoothController.switchBluetooth(bluetoothState);

    }
}