package com.liferay.dxpdemo.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.dxpdemo.R;


public class NotificationsFragment extends AbstractWebContentFragment {


	public static NotificationsFragment newInstance() {
		return new NotificationsFragment();
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_notifications, container, false);
	}

}
