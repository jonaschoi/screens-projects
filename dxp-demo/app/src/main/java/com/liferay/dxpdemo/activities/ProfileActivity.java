package com.liferay.dxpdemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.liferay.dxpdemo.R;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.userportrait.UserPortraitListener;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;

public class ProfileActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		User loggedUser = SessionContext.getCurrentUser();

		EditText name = (EditText) findViewById(R.id.user_name);
		name.setText(loggedUser.getFirstName());

		EditText lastname = (EditText) findViewById(R.id.user_lastname);
		lastname.setText(loggedUser.getLastName());

		EditText email = (EditText) findViewById(R.id.user_email);
		email.setText(loggedUser.getEmail());
	}

}
