package io.github.amarcinkowski.daddylauncher;

import android.app.IntentService;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Created by amarcinkowski on 10.10.16.
 */

public class SmsLoudReaderService extends IntentService {

    static TextToSpeech t1;

    public SmsLoudReaderService(String name) {
        super(name);
    }

    public SmsLoudReaderService() {
        super("SmsLoudReaderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (t1 == null) {
            t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        t1.setLanguage(Locale.getDefault());
                        Log.i("TTS", "init");
                    }
                }
            });
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String dataString = intent.getStringExtra("message");
        Log.i("TEST", "read " + dataString);
        t1.speak(dataString, TextToSpeech.QUEUE_FLUSH, null);
    }

}
