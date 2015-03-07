package blueset.triangles.com.blueset;

import android.app.Application;
import android.content.Context;

/**
 * Created by mittu on 3/7/2015.
 */
public class AppContext extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        AppContext.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return AppContext.context;
    }
}

