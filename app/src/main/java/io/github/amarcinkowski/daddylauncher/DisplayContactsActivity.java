package io.github.amarcinkowski.daddylauncher;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.Collections;
import java.util.List;

/**
 * Created by amarcinkowski on 10.10.16.
 */

public class DisplayContactsActivity extends AppCompatActivity {

    public static int displayWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayWidth = getDisplayWidth();
        setContentView(R.layout.contacts_view);
        createList();
//        Intent intent = getIntent();
//        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
//        TextView textView = new TextView(this);
//        textView.setTextSize(40);
//        textView.setText(message);

//        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
//        layout.addView(textView);
    }

    public void createList() {
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        List<Contact> contacts = new ContactHelper(contentResolver).loadContacts();
        clearContacts();
        // sort
        Collections.sort(contacts, (c1, c2) -> c2.count.compareTo(c1.count));
        // add to view
        for (Contact c : contacts) {
            addContactToView(c);
        }
    }

    private void clearContacts() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.contactsLayout);
        if (layout.getChildCount() > 0) {
            layout.removeAllViews();
        }
    }

    private int getDisplayWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public void addContactToView(Contact contact) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.contactsLayout);
        ContactButton button = new ContactButton(getApplicationContext());
        button.setContact(contact);
        button.setWidth(displayWidth);
        layout.addView(button);
    }


}
