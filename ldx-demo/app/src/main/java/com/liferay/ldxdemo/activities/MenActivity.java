package com.liferay.ldxdemo.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.ldxdemo.R;
import com.liferay.ldxdemo.beacon.NotificationUtil;

/**
 * @author dipenp
 */
public class MenActivity extends Fragment {

	public static MenActivity newInstance() {

		Bundle args = new Bundle();

		MenActivity fragment = new MenActivity();
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.activity_men, container, false);

		//example of sending a 'push' notification
		NotificationUtil.sendNotification(getContext());

		return inflate;
	}

//		setTitle(R.string.title_section3);
//		setTitle(listArray[position]);

}
