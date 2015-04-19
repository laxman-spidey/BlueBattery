package blueset.triangles.com.blueset;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import blueset.triangles.com.blueset.util.LogUtil;


public class MainActivity extends ActionBarActivity  implements View.OnClickListener{

    //ArrayList<>
    private ListView listview;
    public static final String PREFS_NAME = "BlueSetPreferences";
    public static final String SELECTED_DEVICE = "seletedDevice";

    private Button settingsButton;
    private ImageButton bluetoothButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.print("djkfhaskdf");
        initiate();
        setupUICOmponents();
        afterActivityCreation();
    }

    private void setupUICOmponents() {
        settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(this);
        bluetoothButton = (ImageButton) findViewById(R.id.bluetoothButton);
        bluetoothButton.setOnClickListener(this);

    }

    BluetoothController bluetoothController;
    DeviceListAdapter deviceList;
    public void initiate()
    {
        bluetoothController = new BluetoothController();
        //bluetoothController.setBluetoothUp();
        bluetoothController.switchBluetooth(true);
        //deviceList = bluetoothController.discoverDevices();

    }
    private void afterActivityCreation()
    {

        //listview = (ListView) findViewById(R.id.deviceList);
        ArrayList<Integer> imageIdList = new ArrayList<Integer>();

        //getCategoryAttributesFromBuilder(imageIdList);
        //Log.i("tag", "after getting attributes");
        //DeviceListAdapter adapter = new DeviceListAdapter(getApplicationContext(),R.layout.row_layout,deviceList);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.id.list_item);
        //deviceList = new DeviceListAdapter(getApplicationContext(),R.layout.row_layout);
        //listview.setAdapter(deviceList);
        //bluetoothController.startDiscovering();
        //bluetoothController.discoverDevices(deviceList);
        //listview.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        BluetoothDevice selectedDevice = deviceList.getItem(position);
        LogUtil.print("configured item - " +selectedDevice.getName());
        bluetoothController.cancelDiscovery();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(SELECTED_DEVICE, selectedDevice.getAddress());
        editor.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == settingsButton.getId())
        {
            openSettingsActivity();
        }
        if(v.getId() == bluetoothButton.getId())
        {
            openBluetoothConfig();
        }

    }

    private void openSettingsActivity() {

        Intent intent = new Intent();
        intent.setClass(this, blueset.triangles.com.blueset.SettingsActivity.class);
        startActivity(intent);

    }
    private void openBluetoothConfig()
    {
        /*
        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        final ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.bluetooth.bluetoothSettings");
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity( intent);
        */
        Intent intentBluetooth = new Intent();
        intentBluetooth.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
        startActivity(intentBluetooth);

    }
}
