package com.liferay.ldxdemo.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.liferay.ldxdemo.BuildConfig;
import com.liferay.ldxdemo.R;
import com.liferay.ldxdemo.notification.PushService;
import com.liferay.ldxdemo.notification.SnackbarUtil;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.User;

import java.util.Calendar;

//import com.liferay.ldxdemo.beacon.NotificationUtil;

/**
 * @author Javier Gamarra
 */
public class MainActivity extends AppCompatActivity implements LoginListener, View.OnClickListener {

	private LoginScreenlet loginScreenlet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		loginScreenlet = (LoginScreenlet) findViewById(R.id.login_screenlet);
		loginScreenlet.setListener(this);

		if (BuildConfig.DEBUG) {
			setDefaultValuesForUserAndPassword();
		}

		findViewById(R.id.sign_up).setOnClickListener(this);

	}

	@Override
	public void onLoginSuccess(User user) {
		startActivity(new Intent(this, MenuActivity.class));

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				String title = getString(R.string.app_slogan);
				String description = "Near our store today? Hurry in and use your 25% off our Spring Shoe Sale! Click for details.";
				PushService.createGlobalNotification(title, description, getApplicationContext());
			}
		}, 15000);

		launchNotificationInSeconds(5);

	}

	public void launchNotificationInSeconds(int seconds) {
		Context applicationContext = this.getApplicationContext();
		Intent intent = new Intent(applicationContext, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, intent, 0);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, seconds);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
	}

	@Override
	public void onLoginFailure(Exception e) {
		SnackbarUtil.showMessage(this, "Login failed!");
	}

	private void setDefaultValuesForUserAndPassword() {
		EditText login = (EditText) findViewById(R.id.liferay_login);
		login.setText(R.string.default_user);

		EditText password = (EditText) findViewById(R.id.liferay_password);
		password.setText(R.string.default_password);
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(this, SignUpActivity.class));
	}
}
