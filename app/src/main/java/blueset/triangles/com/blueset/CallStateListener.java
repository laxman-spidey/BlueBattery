package blueset.triangles.com.blueset;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import blueset.triangles.com.blueset.util.LogUtil;

/**
 * Created by mittu on 3/7/2015.
 */
public class CallStateListener extends PhoneStateListener {
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING: {
                LogUtil.print("call recieved from "+incomingNumber);
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
    }

    BluetoothController bluetoothController;
    public void onPhoneRinging()
    {
        bluetoothController = new BluetoothController();
        bluetoothController.switchBluetooth(true);
        bluetoothController.connectToHeadset();
    }


}
