package io.github.amarcinkowski.daddylauncher;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.util.Arrays;

/**
 * Created by amarcinkowski on 09.10.16.
 */

public class PermsHelper {

    public final static int WRITE_PERMS = 21;
    public final static int CONTACTS_PERMS = 1;
    public final static int CALL_PERMS = 2;

    public static void grantPermission(Activity activity, String[] perms, int permsCode) {
        for (String perm : perms) {
            int permissionCheck = ContextCompat.checkSelfPermission(activity, perm);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                Log.i("grant-perms", Arrays.toString(perms));
                ActivityCompat.requestPermissions(
                        activity,
                        perms,
                        permsCode);
            }
        }
    }

    public static void writeSettingsPerm(Context context, Activity activity) {
        boolean permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(context);
        } else {
            permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
        }
        if (permission) {
            Log.i("WRITE PERMS", "OK!");
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                activity.startActivityForResult(intent, FullscreenActivity.CODE_WRITE_SETTINGS_PERMISSION);
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_SETTINGS}, FullscreenActivity.CODE_WRITE_SETTINGS_PERMISSION);
            }
        }
    }

}
