package blueset.triangles.com.blueset;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;
import blueset.triangles.com.blueset.util.LogUtil;

/**
 * Created by mittu on 11/7/2015.
 */
public class CallStateHandlerService extends IntentService {

    public CallStateHandlerService() {
        super("CALL_STATE_HANDLER");
    }
    public CallStateHandlerService(String name) {
        super(name);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtil.print("handling Intent");
        Toast.makeText(getApplicationContext(),"handling intent ",Toast.LENGTH_SHORT);
        BluetoothController bluetoothController;
        bluetoothController = new BluetoothController();
        boolean state = intent.getBooleanExtra("switchBluetoothToState", false);
        bluetoothController.switchBluetooth(state);

    }
}