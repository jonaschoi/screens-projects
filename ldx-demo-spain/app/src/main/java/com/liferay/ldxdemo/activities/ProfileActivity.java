package com.liferay.ldxdemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.liferay.ldxdemo.R;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.userportrait.UserPortraitListener;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;

public class ProfileActivity extends AppCompatActivity implements UserPortraitListener {

	private UserPortraitScreenlet userPortraitScreenlet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_profile);

		User loggedUser = SessionContext.getCurrentUser();

		EditText name = (EditText) findViewById(R.id.user_name);
		name.setText(loggedUser.getFirstName());

		EditText lastname = (EditText) findViewById(R.id.user_lastname);
		lastname.setText(loggedUser.getLastName());

		EditText email = (EditText) findViewById(R.id.user_email);
		email.setText(loggedUser.getEmail());

		userPortraitScreenlet = (UserPortraitScreenlet) findViewById(R.id.user_portrait_profile);
		userPortraitScreenlet.setListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			userPortraitScreenlet.upload(requestCode, data);
		}
	}

	@Override
	public Bitmap onUserPortraitLoadReceived(UserPortraitScreenlet source, Bitmap bitmap) {
		return null;
	}

	@Override
	public void onUserPortraitLoadFailure(UserPortraitScreenlet source, Exception e) {

	}

	@Override
	public void onUserPortraitUploaded(UserPortraitScreenlet source) {

	}

	@Override
	public void onUserPortraitUploadFailure(UserPortraitScreenlet source, Exception e) {

	}

	@Override
	public void loadingFromCache(boolean success) {

	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {

	}

	@Override
	public void storingToCache(Object object) {

	}
}
