package com.liferay.dxpdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.liferay.dxpdemo.BuildConfig;
import com.liferay.dxpdemo.R;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.User;

public class MainActivity extends AppCompatActivity implements LoginListener, View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LoginScreenlet loginScreenlet = (LoginScreenlet) findViewById(R.id.login_screenlet);
		loginScreenlet.setListener(this);

		if (BuildConfig.DEBUG) {
			TextView login = (TextView) loginScreenlet.findViewById(R.id.liferay_login);
			login.setText(R.string.default_user);
			TextView password = (TextView) loginScreenlet.findViewById(R.id.liferay_password);
			password.setText(R.string.default_password);
		}
	}

	@Override
	public void onLoginSuccess(User user) {
		startActivity(new Intent(this, MenuActivity.class));
	}

	@Override
	public void onLoginFailure(Exception e) {
		View content = findViewById(android.R.id.content);
		Snackbar.make(content, "Error logging in", Snackbar.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(this, SignUpActivity.class));
	}
}
