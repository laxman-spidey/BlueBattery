package blueset.triangles.com.blueset;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import blueset.triangles.com.blueset.util.AppServiceStateUtil;
import blueset.triangles.com.blueset.util.ConstantUtil;
import blueset.triangles.com.blueset.util.SharedPrefUtil;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends Activity
{
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;

    private View mContentView;
    private View mControlsView;
    private boolean mVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        mContentView = findViewById(R.id.imageView);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        splashProcess();
    }



    private void splashProcess()
    {
        Thread splashThread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    int waited = 0;
                    while (waited < 2000)
                    {
                        sleep(100);
                        waited += 100;
                    }
                } catch (InterruptedException e)
                {
                    // do nothing
                } finally
                {
                    finish();
                    showMainActivity();

                }
            }
        };
        splashThread.start();
        processFirstTimeUsage();
    }
    public void showMainActivity ()
    {

        Intent intent = new Intent();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            intent.setClass(this, blueset.triangles.com.blueset.WelcomePermissionScreen.class);
        }
        else
        {
            intent.setClass(this,blueset.triangles.com.blueset.MainActivity.class);
        }
        startActivity(intent);
    }

    private void processFirstTimeUsage()
    {
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getApplicationContext());
        if(sharedPrefUtil.getFirstTimeUse())
        {
            AppServiceStateUtil.setServiceState(getApplicationContext(), true);
            sharedPrefUtil.setFirstTimeUse(false);
        }
    }
}
