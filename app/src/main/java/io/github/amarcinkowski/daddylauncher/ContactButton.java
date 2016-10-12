package io.github.amarcinkowski.daddylauncher;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by amarcinkowski on 10.10.16.
 */

public class ContactButton extends Button {

	public static final int HUGE = 69;
	public static final int BIG = 53;
	public static final int MEDIUM = 42;
	public static final int SMALL = 36;
	public static final int SMALLER = 32;

	public void setContact(Contact contact) {
		setText(contact.name);
		int fontSize = getFontSize(contact.name.length());
		setTextSize(fontSize);
		setOnLongClickListener(new CallOnClickListener(getContext(), contact));
	}

	private int getFontSize(int letters) {
		int size = letters / 5;
		switch (size) {
		case 0:
			return HUGE;
		case 1:
			return BIG;
		case 2:
			return MEDIUM;
		case 3:
			return SMALL;
		default:
			return SMALLER;
		}
	}

	private void setLayout() {
		setHeight(400);
		setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		Typeface typeface = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);
		setTypeface(typeface);
	}

	public ContactButton(Context context) {
		super(context);
		setLayout();
	}

}
