package com.liferay.ldxdemo.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.liferay.ldxdemo.R;
import com.liferay.ldxdemo.notification.PushService;

/**
 * @author Javier Gamarra
 */
public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String title = context.getString(R.string.thanks_for_buying);
		String description = context.getString(R.string.review);
		PushService.createGlobalNotification(title, description, context.getApplicationContext(), R.id.review);
	}
}
