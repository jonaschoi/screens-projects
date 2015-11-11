package com.liferay.ldxdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.liferay.ldxdemo.R;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.User;

/**
 * @author Javier Gamarra
 */
public class MainActivity extends AppCompatActivity implements LoginListener, View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LoginScreenlet loginScreenlet = (LoginScreenlet) findViewById(R.id.login_screenlet);
		loginScreenlet.setListener(this);

		setDefaultValuesForUserAndPassword();

		findViewById(R.id.sign_up).setOnClickListener(this);
	}

	@Override
	public void onLoginSuccess(User user) {
		startActivity(new Intent(this, MenuActivity.class));
	}

	@Override
	public void onLoginFailure(Exception e) {

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
