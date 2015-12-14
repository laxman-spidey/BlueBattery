package blueset.triangles.com.blueset;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.telephony.TelephonyManager;

import java.lang.reflect.Constructor;

import blueset.triangles.com.blueset.util.ConstantUtil;
import blueset.triangles.com.blueset.util.LogUtil;
import blueset.triangles.com.blueset.util.SharedPrefUtil;

public class MusicStateChangeService extends IntentService {

    SharedPrefUtil sharedPrefUtil;
    public MusicStateChangeService() {
        super(ConstantUtil.MUSIC_STATE);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        LogUtil.print("service started");
        sharedPrefUtil = new SharedPrefUtil(getApplicationContext());
        if (intent != null) {
            if (intent.hasExtra(ConstantUtil.MUSIC_STATE)) {
                String previousState = ConstantUtil.CALL_STATE_NONE;
                boolean prevState = !(intent.getBooleanExtra(ConstantUtil.INTENT_ACTION_PLAYING, false));
                if(prevState == true)
                {
                    previousState = ConstantUtil.MUSIC_STATE_PLAY;
                }
                else if(prevState == false)
                {
                    previousState = ConstantUtil.MUSIC_STATE_STOP;
                }
                if (previousState.equals(ConstantUtil.MUSIC_STATE_STOP)) {
                    handleMusicIntent(intent);
                } else {
                    //total 10 sec
                    for (int i = 0; i <= 01; i++) {

                        try {
                            Thread.sleep(1000); //every 1 sec
                            LogUtil.print(i + "....");
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (sharedPrefUtil.getCallStateFromSharedPref().equals(ConstantUtil.CALL_STATE_CONNECTED))
                    {
                        sharedPrefUtil.setMusicState(ConstantUtil.MUSIC_STATE_PLAY);
                    }
                    else
                    {
                        handleMusicIntent(intent);
                    }
                }
            }
        }
    }


    protected void handleMusicIntent(Intent intent) {
        LogUtil.print("handling intent");
        if (intent.getBooleanExtra(ConstantUtil.INTENT_ACTION_PLAYING, false)) {
            sharedPrefUtil.setMusicState(ConstantUtil.MUSIC_STATE_PLAY);
            sendBluetoothAction(true);
        } else {
            sharedPrefUtil.setMusicState(ConstantUtil.MUSIC_STATE_STOP);
            //sendBluetoothAction(false);
        }


    }
    private void sendBluetoothAction(boolean bluetoothState)
    {
        LogUtil.print("turning bluetooth " + bluetoothState);
        BluetoothController bluetoothController = new BluetoothController();
        bluetoothController.switchBluetooth(bluetoothState);
    }
}

