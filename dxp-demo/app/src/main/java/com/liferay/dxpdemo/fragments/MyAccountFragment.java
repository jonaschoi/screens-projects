package com.liferay.dxpdemo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.dxpdemo.R;
import com.liferay.dxpdemo.activities.MenuActivity;

public class MyAccountFragment extends AbstractWebContentFragment implements View.OnClickListener {

	public static MyAccountFragment newInstance() {
		return new MyAccountFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_my_account, container, false);
	}

	@Override
	public void onClick(View v) {
		((MenuActivity) getActivity()).inflateFragmentAtPosition(2);
	}
}
