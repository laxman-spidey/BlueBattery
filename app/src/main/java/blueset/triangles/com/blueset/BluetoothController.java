package blueset.triangles.com.blueset;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.telephony.PhoneStateListener;

import java.util.Set;

import blueset.triangles.com.blueset.util.LogUtil;

/**
 * Created by mittu on 3/7/2015.
 */
public class BluetoothController {

    BluetoothAdapter mBluetoothAdapter;
    public BluetoothController()
    {
        setBluetoothUp();
    }

    public void setBluetoothUp()
    {
         mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }
    }

    public static boolean switchBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            LogUtil.print("enabling bluetooth");
            return bluetoothAdapter.enable();
        }
        else if(!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }
        // No need to change bluetooth state
        return true;
    }

    public void connectToHeadset() {

    }

    Set<BluetoothDevice> pairedDevices;
    public void getPairedDevices()
    {
         pairedDevices = mBluetoothAdapter.getBondedDevices();
// If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView

            }
        }
    }

}
