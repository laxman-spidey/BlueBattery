package blueset.triangles.com.blueset;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.Toast;

import blueset.triangles.com.blueset.util.AppServiceStateUtil;
import blueset.triangles.com.blueset.util.ConstantUtil;
import blueset.triangles.com.blueset.util.LogUtil;

/**
 * Created by mittu on 11/28/2015.
 */
public class WidgetBroadcastReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        LogUtil.print("On receive " + intent.getAction());
        if (intent.getAction().equals(ConstantUtil.WIDGET_UPDATE_ACTION))
        {
            LogUtil.print("widget update action intent");
            boolean previousState = AppServiceStateUtil.getServiceState(context);
            AppServiceStateUtil.setServiceState(context, !previousState);
            updateServiceToggleButton(context);

        }
    }

    public void updateServiceToggleButton(Context context)
    {

        LogUtil.print("update Service Toggle button");
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        remoteViews.setOnClickPendingIntent(R.id.widget_toggle_button, NewAppWidget.buildButtonPendingIntent(context));
        NewAppWidget.updateWidgetIcon(context,remoteViews);
        NewAppWidget.pushWidgetUpdate(context.getApplicationContext(), remoteViews);
    }
}
