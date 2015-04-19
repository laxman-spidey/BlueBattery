package blueset.triangles.com.blueset.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import blueset.triangles.com.blueset.AppContext;
import blueset.triangles.com.blueset.CallStateListener;

/**
 * Created by laxman on 11/4/15.
 */
public class SettingsUtil implements SharedPreferences.OnSharedPreferenceChangeListener {
    private Context context;
    public static final String PREFS_NAME = "BlueSetPreferences";
    public static final String SELECTED_DEVICE = "seletedDevice";
    public static final String OUT_GOING_CALLS_CONFIG = "outgoing_calls";
    public static final String SERVICE_ENABLED_CONFIG = "enable_service";
    private SharedPreferences.Editor editor;
    private SharedPreferences settings ;
    public SettingsUtil(Context context)
    {

        this.context = context;
        //settings = context.getSharedPreferences(PREFS_NAME, 0);
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        editor = settings.edit();

    }
    public SettingsUtil()
    {

        this.context = AppContext.getAppContext();
        //settings = context.getSharedPreferences(PREFS_NAME, 0);
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        editor = settings.edit();
        LogUtil.print("service == " +isServiceEnabled());
    }


    public void checkForOutgoingCalls(boolean state)
    {
        editor.putBoolean(OUT_GOING_CALLS_CONFIG, state);
        editor.commit();
    }
    public boolean getOutGoingCallsConfig()
    {
        return settings.getBoolean(OUT_GOING_CALLS_CONFIG,true);
    }
    public void setServiceEnable(boolean state)
    {
        editor.putBoolean(OUT_GOING_CALLS_CONFIG, state);
        editor.commit();
    }
    public boolean isServiceEnabled()
    {
        return settings.getBoolean(SERVICE_ENABLED_CONFIG,true);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        LogUtil.print("changed");
        if (key.equals(SERVICE_ENABLED_CONFIG))
        {
            if(isServiceEnabled())
            {
                CallStateListener.getCallStateListenerObject().unregisterCallStateListener();
            }
            else
            {
                CallStateListener.getCallStateListenerObject().registerCallStateListener();
            }
        }
    }
    public void registerOnPreferenceChangeListener()
    {
        LogUtil.print("registering preference listener");
        settings.registerOnSharedPreferenceChangeListener(this);

    }

}

