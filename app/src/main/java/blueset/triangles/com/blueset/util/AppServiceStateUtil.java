package blueset.triangles.com.blueset.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mittu on 11/28/2015.
 */
public class AppServiceStateUtil
{
    public static boolean getServiceState(Context context)
    {
        SharedPreferences sharedPref1 = context.getApplicationContext().getSharedPreferences(ConstantUtil.BLUE_SHARED_PREF, Context.MODE_PRIVATE);
        boolean serviceState = sharedPref1.getBoolean(ConstantUtil.APP_SERVICE_STATE, false);
        LogUtil.print("serviceState " + serviceState);
        return serviceState;

    }
    public static void setServiceState(Context context, boolean serviceState)
    {
        SharedPreferences sharedPref1 = context.getApplicationContext().getSharedPreferences(ConstantUtil.BLUE_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref1.edit();
        editor.putBoolean(ConstantUtil.APP_SERVICE_STATE, serviceState);
        LogUtil.print("service state set to  " +serviceState);
        editor.commit();

    }
}
