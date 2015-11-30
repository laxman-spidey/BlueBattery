package blueset.triangles.com.blueset;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.provider.MediaStore;
import android.telephony.PhoneStateListener;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
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
            LogUtil.print("disabling bluetooth");
            return bluetoothAdapter.disable();
        }
        // No need to change bluetooth state
        return true;
    }

    public boolean isEnabled()
    {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter.isEnabled();
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
    private  BroadcastReceiver mReceiver;
    DeviceListAdapter deviceList;
    public ArrayAdapter<BluetoothDevice> discoverDevices( DeviceListAdapter deviceListAdapter)
    {
        this.deviceList = deviceListAdapter;
         mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    deviceList.add(device);
                    // Add the name and address to an array adapter to show in a ListView
                    //mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            }
        };
        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);



        return deviceList;
    }

    ArrayAdapter<String> mArrayAdapter;
    public void discoverDevicesStrings(ArrayAdapter<String> stringArrayAdapter)
    {
        this.mArrayAdapter = stringArrayAdapter;
        mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a device
                LogUtil.print("discovering devices");
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    //deviceList.add(device);
                    // Add the name and address to an array adapter to show in a ListView
                    LogUtil.print(device.getName() + "\n" + device.getAddress());
                    mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            }
        };
        // Register the BroadcastReceiver
        LogUtil.print("discover called");
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);


        //return deviceList;
    }
    public void startDiscovering()
    {
        mBluetoothAdapter.startDiscovery();
    }
    public void cancelDiscovery()
    {
        mBluetoothAdapter.cancelDiscovery();
    }
}
