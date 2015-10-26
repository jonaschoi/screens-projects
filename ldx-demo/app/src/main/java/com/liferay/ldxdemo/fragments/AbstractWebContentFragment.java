package com.liferay.ldxdemo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.liferay.ldxdemo.R;
import com.liferay.ldxdemo.activities.FragmentLoaded;

/**
 * @author Javier Gamarra
 */
public abstract class AbstractWebContentFragment extends Fragment {

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		FragmentLoaded activity = (FragmentLoaded) getActivity();
		activity.onFragmentLoaded(getView().findViewById(R.id.liferay_webview), false);
	}

}
