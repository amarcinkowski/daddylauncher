package io.github.amarcinkowski.daddylauncher;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@ link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
//    private static final boolean AUTO_HIDE = true;

    /**
     * If {@ link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
//    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    public static final int CODE_WRITE_SETTINGS_PERMISSION = 520;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fullscreen);
/*
        setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
*/

        PermsHelper.writeSettingsPerm(getApplicationContext(), this);

        final Button contactsButton = (Button) findViewById(R.id.contacts_button);
        contactsButton.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        contactsButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplayContactsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            Log.i("Home Button", "Clicked");
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }


    private void setRingtone(Context context) {
        Uri u = Uri.parse("android.resource://" + context.getPackageName() + "/raw/" + R.raw.kronika);

        ContentValues values = new ContentValues();
        long current = System.currentTimeMillis();
        values.put(MediaStore.MediaColumns.DATA, u.getPath());
        values.put(MediaStore.MediaColumns.TITLE, "title");
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp3");
        values.put(MediaStore.Audio.Media.ARTIST, "ringtone");
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        values.put(MediaStore.Audio.Media.IS_ALARM, true);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        getContentResolver().insert(MediaStore.Audio.Media.getContentUriForPath(u.getPath()), values);

        RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, u);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        PermsHelper.grantPermission(this, new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_CALL_LOG
        }, 312);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i("REQ RES", Arrays.toString(permissions) + "==>" + requestCode);
        if (Arrays.equals(grantResults, new int[]{PackageManager.PERMISSION_GRANTED})) {
            switch (requestCode) {
                case 321:
                    Log.i("REQ PERMS RESULT", "!!!!");
                    break;
                case 520:
                    Log.i("RINGTONE", "!!!!");
                    setRingtone(this);
                    break;
            }
        }

    }


}
