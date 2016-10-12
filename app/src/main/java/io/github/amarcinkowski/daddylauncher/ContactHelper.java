package io.github.amarcinkowski.daddylauncher;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amarcinkowski on 10.10.16.
 */

public class ContactHelper {

    public static final String CONTENT_CALL_LOG_CALLS = "content://call_log/calls";

    private ContentResolver contentResolver;

    public ContactHelper(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    private static String normalizeNumber(String number) {
        String normalized = PhoneNumberUtils.extractNetworkPortion(number);
        normalized = normalized.length() > 9 ? normalized.substring(normalized.length() - 9) : normalized;
        return normalized;
    }

    private int getCallCount(String number) {
        String[] args = new String[]{String.format("\\u0025%s\\u0025", number)};
        Cursor countCursor = contentResolver.query(Uri.parse(CONTENT_CALL_LOG_CALLS), null, CallLog.Calls.NUMBER + " LIKE ?", args, null);
        int count = countCursor.getCount();
        return count;
    }

    public List<Contact> loadContacts() {
        List<Contact> contacts = new ArrayList<>();
        Cursor cur = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, ContactsContract.Contacts.STARRED + "='1'", null, null);
        if (cur != null && cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                // has phone numbers
                if (hasNumbers(cur)) {
                    String number = getPhoneNumber(id);
                    int count = getCallCount(number);
                    contacts.add(new Contact(name, number, count));
                }
            }
        }
        return contacts;
    }

    private boolean hasNumbers(Cursor cur) {
        int columnIndex = cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        String numberCountString = cur.getString(columnIndex);
        int numberCount = Integer.parseInt(numberCountString);
        return numberCount > 0;
    }


    private String getPhoneNumber(String id) {
        Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{id}, null);
        while (pCur.moveToNext()) {
            String phone = pCur.getString(
                    pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            return normalizeNumber(phone);
        }
        pCur.close();
        return "?";
    }

}
