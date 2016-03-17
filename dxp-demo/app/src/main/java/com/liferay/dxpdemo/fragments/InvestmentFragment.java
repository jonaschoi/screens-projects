package com.liferay.dxpdemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.dxpdemo.R;

public class InvestmentFragment extends AbstractWebContentFragment {

	public static InvestmentFragment newInstance() {
		return new InvestmentFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_investment, container, false);
	}

}
