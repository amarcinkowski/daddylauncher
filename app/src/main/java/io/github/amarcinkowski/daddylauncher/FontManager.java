package io.github.amarcinkowski.daddylauncher;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by amarcinkowski on 09.10.16.
 */

public class FontManager {

    private static final String ROOT = "fonts/";
    public static final String FONTAWESOME = ROOT + "fontawesome-webfont.ttf";
    public static final String CHUNK5 = ROOT + "Chunkfive.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

}
