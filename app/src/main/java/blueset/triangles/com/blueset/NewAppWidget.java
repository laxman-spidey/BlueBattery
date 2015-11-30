package blueset.triangles.com.blueset;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import blueset.triangles.com.blueset.util.AppServiceStateUtil;
import blueset.triangles.com.blueset.util.ConstantUtil;
import blueset.triangles.com.blueset.util.LogUtil;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider
{

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        LogUtil.print("on update");
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++)
        {
            LogUtil.print("updating " + i);
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }


    @Override
    public void onEnabled(Context context)
    {
        LogUtil.print("on enabled");
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context)
    {
        LogUtil.print("on disabled");
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
    {
        LogUtil.print("update app widget");
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        //views.setTextViewText(R.id.imageButton, widgetText);
        updateWidgetIcon(context,views);
        views.setOnClickPendingIntent(R.id.widget_toggle_button, buildButtonPendingIntent(context));


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static PendingIntent buildButtonPendingIntent(Context context)
    {
        //++MyWidgetIntentReceiver.clickCount;
        LogUtil.print("build pending intent");
        // initiate widget update request
        Intent intent = new Intent();
        intent.setAction(ConstantUtil.WIDGET_UPDATE_ACTION);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews)
    {
        LogUtil.print("push widget update");

        ComponentName myWidget = new ComponentName(context, NewAppWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }
    public static void updateWidgetIcon(Context context, RemoteViews remoteViews)
    {
        LogUtil.print("update icon");
        if (AppServiceStateUtil.getServiceState(context) == false)
        {
            remoteViews.setInt(R.id.widget_toggle_button,"setBackgroundResource",R.drawable.widget_service_off);
        }
        else
        {
            remoteViews.setInt(R.id.widget_toggle_button,"setBackgroundResource",R.drawable.ic_launcher);
        }
    }
}

