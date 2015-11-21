package blueset.triangles.com.blueset;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import blueset.triangles.com.blueset.util.LogUtil;

/**
 * Created by mittu on 11/16/2015.
 */
public class MediaStateReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        LogUtil.print("broadcast event recieved media " + intent.getAction());
        if(intent.hasExtra("playing")) {
            Intent mediaIntent = new Intent(context, MusicStateChangeService.class);
            mediaIntent.putExtra("MUSIC_STATE", true);
            mediaIntent.putExtra("playing",intent.getBooleanExtra("playing",false));
            context.startService(mediaIntent);
        }
    }
}
