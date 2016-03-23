package com.liferay.dxpdemo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.dxpdemo.R;
import com.liferay.dxpdemo.activities.MenuActivity;
import com.liferay.mobile.screens.webcontentdisplay.WebContentDisplayListener;
import com.liferay.mobile.screens.webcontentdisplay.WebContentDisplayScreenlet;

public class MyAccountFragment extends AbstractWebContentFragment implements WebContentDisplayListener {

	public static MyAccountFragment newInstance() {
		return new MyAccountFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_account, container, false);

		WebContentDisplayScreenlet webContentDisplayScreenlet = (WebContentDisplayScreenlet) view.findViewById(R.id.accounts);
		webContentDisplayScreenlet.setListener(this);

		return view;
	}

	@Override
	public String onWebContentReceived(WebContentDisplayScreenlet source, String html) {
		return null;
	}

	@Override
	public void onWebContentFailure(WebContentDisplayScreenlet source, Exception e) {

	}

	@Override
	public void onWebContentClicked(MotionEvent event) {
		((MenuActivity) getActivity()).inflateFragmentAtPosition(1);
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
