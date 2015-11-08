package blueset.triangles.com.blueset;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

import blueset.triangles.com.blueset.util.LogUtil;
import blueset.triangles.com.blueset.util.SettingsUtil;

/**
 * Created by mittu on 3/7/2015.
 */
public class CallStateListener extends PhoneStateListener {

    public static int isListening;
    public static final int LISTENING = 0;
    public static final int NOT_LISTENING = 1;
    private boolean isBluetoothAlreadyEnabled;
    private static CallStateListener singltonObject;
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING: {
                LogUtil.print("call recieved from "+incomingNumber);
                onPhoneRinging();
                break;
            }
            case TelephonyManager.CALL_STATE_OFFHOOK:
            {
                LogUtil.print("call offhook" + incomingNumber);

                break;
            }
            case TelephonyManager.CALL_STATE_IDLE:
            {
                LogUtil.print("call Idle"+incomingNumber);
                onPhoneDisconnected();
                incomingNumber = "0";
                break;
            }
        }


    }

    private CallStateListener()
    {
        initiate();
    }
    public static CallStateListener getCallStateListenerObject()
    {
        if(singltonObject == null)
        {
            singltonObject = new CallStateListener();

        }
        return singltonObject;
    }
    public void initiate()
    {
        //SettingsUtil settingsUtil = new SettingsUtil();
        //if(settingsUtil.isServiceEnabled())
            registerCallStateListener();

    }

    BluetoothController bluetoothController;

    public void onPhoneRinging()  {

        bluetoothController = new BluetoothController();
        isBluetoothAlreadyEnabled = bluetoothController.isEnabled();
        bluetoothController.switchBluetooth(true);


        bluetoothController.connectToHeadset();
    }
    public void onPhoneDisconnected()
    {
        if(!isBluetoothAlreadyEnabled)
        {
            bluetoothController.switchBluetooth(false);
        }
    }

    public void registerCallStateListener()
    {
        LogUtil.print("registering call state listner");
        TelephonyManager tm = (TelephonyManager) AppContext.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(this, PhoneStateListener.LISTEN_CALL_STATE);
        isListening = LISTENING;
    }
    public void unregisterCallStateListener()
    {
        LogUtil.print("unregistering call state listner");
        TelephonyManager tm = (TelephonyManager) AppContext.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(this, LISTEN_NONE);

    }



}
