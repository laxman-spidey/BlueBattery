package blueset.triangles.com.blueset;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import blueset.triangles.com.blueset.util.LogUtil;

/**
 * Created by mittu on 3/7/2015.
 */
public class CallStateListener extends PhoneStateListener {

    public static int isListening;
    public static final int LISTENING = 0;
    public static final int NOT_LISTENING = 1;
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING: {
                LogUtil.print("call recieved from "+incomingNumber);
                onPhoneRinging();
                break;
            }
            case TelephonyManager.CALL_STATE_OFFHOOK:
            {
                LogUtil.print("call offhook"+incomingNumber);
            }
            case TelephonyManager.CALL_STATE_IDLE:
            {
                LogUtil.print("call Idle"+incomingNumber);
                onPhoneDisconneccted();
                break;
            }
        }

    }

    public CallStateListener()
    {
        initiate();
    }
    public void initiate()
    {

        TelephonyManager tm = (TelephonyManager) AppContext.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(this, PhoneStateListener.LISTEN_CALL_STATE);
        isListening = LISTENING;
    }

    BluetoothController bluetoothController;
    public void onPhoneRinging()
    {
        bluetoothController = new BluetoothController();
        bluetoothController.switchBluetooth(true);

        bluetoothController.connectToHeadset();
    }
    public void onPhoneDisconneccted()
    {
        bluetoothController.switchBluetooth(false);
    }



}
