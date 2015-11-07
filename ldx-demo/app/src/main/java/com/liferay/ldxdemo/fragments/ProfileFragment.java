package com.liferay.ldxdemo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.liferay.ldxdemo.R;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;

public class ProfileFragment extends Fragment {

	public static ProfileFragment newInstance() {
		ProfileFragment fragment = new ProfileFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile, container, false);

		User loggedUser = SessionContext.getLoggedUser();

		EditText name = (EditText) view.findViewById(R.id.user_name);
		name.setText(loggedUser.getFirstName());

		EditText lastname = (EditText) view.findViewById(R.id.user_lastname);
		lastname.setText(loggedUser.getLastName());

		EditText email = (EditText) view.findViewById(R.id.user_email);
		email.setText(loggedUser.getEmail());

		return view;
	}

}
