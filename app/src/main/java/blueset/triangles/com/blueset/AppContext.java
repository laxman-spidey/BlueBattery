package blueset.triangles.com.blueset;

import android.app.Application;
import android.content.Context;

import blueset.triangles.com.blueset.util.LogUtil;

/**
 * Created by mittu on 3/7/2015.
 */
public class AppContext extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        LogUtil.print("app started");
        AppContext.context = getApplicationContext();
        registerListener();
    }

    public static Context getAppContext() {
        return AppContext.context;
    }

    public void registerListener()
    {

        LogUtil.print("registering listener");
        //CallStateListener callStateListener = CallStateListener.getCallStateListenerObject();
        //callStateListener.initiate();
    }
}

