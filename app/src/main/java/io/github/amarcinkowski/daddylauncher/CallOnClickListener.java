package io.github.amarcinkowski.daddylauncher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

/**
 * Created by amarcinkowski on 11.10.16.
 *
 * XXX Long click in contacts is needed to make a call
 */

public class CallOnClickListener implements View.OnLongClickListener {

    private Contact contact;
    private Context context;

    public CallOnClickListener(Context context, Contact contact) {
        this.contact = contact;
        this.context = context;
    }

    private void call(String number) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("tel:" + number));
            context.startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        call(contact.number);
        return true;
    }

}
