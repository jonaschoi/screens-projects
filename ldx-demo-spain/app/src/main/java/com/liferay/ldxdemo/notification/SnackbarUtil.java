package com.liferay.ldxdemo.notification;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;

import com.liferay.ldxdemo.R;

/**
 * @author Javier Gamarra
 */
public class SnackbarUtil {

	public static void showMessage(Activity activity, String message) {
		View content = activity.findViewById(android.R.id.content);
		Snackbar snackbar = Snackbar.make(content, message, Snackbar.LENGTH_LONG);
		int color = ResourcesCompat.getColor(activity.getResources(), R.color.colorPrimary, activity.getTheme());
		snackbar.getView().setBackgroundColor(color);
		snackbar.show();
	}
}
