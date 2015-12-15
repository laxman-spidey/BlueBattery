package blueset.triangles.com.blueset;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;

import blueset.triangles.com.blueset.util.AppServiceStateUtil;
import blueset.triangles.com.blueset.util.ConstantUtil;
import blueset.triangles.com.blueset.util.SharedPrefUtil;

/**
 * Created by mittu on 12/15/2015.
 */
public class BluetoothStateChangeHandlerService extends IntentService
{
    SharedPrefUtil sharedPrefUtil;
    public BluetoothStateChangeHandlerService() {
        super(ConstantUtil.BLUETOOTH_STATE_CHANGE_ACTION);
    }
    @Override
    protected void onHandleIntent(Intent intent)
    {
        sharedPrefUtil = new SharedPrefUtil(getApplicationContext());
        int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
        if(state == BluetoothAdapter.STATE_OFF)
        {
            sharedPrefUtil.setBluetoothSessionState(ConstantUtil.BLUEOOTH_SESSION_OFF);
        }
        else if(state == BluetoothAdapter.STATE_ON)
        {
            //checking if the bluetooth state is already set to auto mode by music/call event
            if(sharedPrefUtil.getBluetoothSessionState().equals(ConstantUtil.BLUEOOTH_SESSION_ON_AUTO))
            {
                //don't set state to manual
                return;
            }
            //else set the state to manual. i.e., the bluetooth is turned on automatically
            sharedPrefUtil.setBluetoothSessionState(ConstantUtil.BLUEOOTH_SESSION_ON_MANUALLY);
        }

    }
}