package com.liferay.ldxdemo.beacon;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.liferay.ldxdemo.R;
import com.liferay.ldxdemo.fragments.WalletFragment;

/**
 * @author Javier Gamarra
 */
public class NotificationUtil {

	public static final int NOTIFICATION_ID = 2;

	public static void sendNotification(Context context) {
		String title = context.getString(R.string.app_slogan);
		String description = "Looking at shoes? Get a sweet discount! Click for details.";

		createGlobalNotification(context.getApplicationContext(), title, description);
	}

	private static void createGlobalNotification(Context context, String title, String description) {
		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
				.setContentTitle(title)
				.setContentText(description)
				.setAutoCancel(true)
				.setSound(uri)
				.setVibrate(new long[]{2000, 1000, 2000, 1000})
				.setSmallIcon(R.drawable.liferay_glyph);

		builder.setContentIntent(createPendingIntentForNotifications(context));

		Notification notification = builder.build();
		NotificationManager notificationManager =
				(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID, notification);
	}

	private static PendingIntent createPendingIntentForNotifications(Context context) {
		Intent resultIntent = new Intent(context, WalletFragment.class);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addNextIntent(resultIntent);
		return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
	}
}
