package com.liferay.dxpdemo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.liferay.dxpdemo.R;
import com.liferay.dxpdemo.activities.FragmentLoaded;

/**
 * Created by Jonas Choi on 02/16/2016.
 */
public class AbstractDDLFormFragment extends Fragment {

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		FragmentLoaded activity = (FragmentLoaded) getActivity();
		activity.onFragmentLoaded(getView().findViewById(R.id.liferay_webview), false);
	}
}
