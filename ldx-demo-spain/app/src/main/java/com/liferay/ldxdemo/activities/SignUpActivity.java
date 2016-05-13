package com.liferay.ldxdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liferay.ldxdemo.R;
import com.liferay.mobile.screens.auth.signup.SignUpListener;
import com.liferay.mobile.screens.auth.signup.SignUpScreenlet;
import com.liferay.mobile.screens.context.User;

public class SignUpActivity extends AppCompatActivity implements SignUpListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		SignUpScreenlet signUpScreenlet = (SignUpScreenlet) findViewById(R.id.signup_screenlet);
		signUpScreenlet.setListener(this);
	}

	@Override
	public void onSignUpFailure(Exception e) {

	}

	@Override
	public void onSignUpSuccess(User user) {
		startActivity(new Intent(this, MenuActivity.class));
	}
}
