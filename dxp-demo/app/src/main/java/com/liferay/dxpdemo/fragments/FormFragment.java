package com.liferay.dxpdemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.dxpdemo.R;
import com.liferay.dxpdemo.activities.MenuActivity;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONObject;


public class FormFragment extends AbstractDDLFormFragment implements DDLFormListener {

	public static FormFragment newInstance() {

		Bundle args = new Bundle();

		FormFragment fragment = new FormFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.content_form, container, false);

		DDLFormScreenlet ddlFormScreenlet = (DDLFormScreenlet) view.findViewById(R.id.form_transfer);
		ddlFormScreenlet.setListener(this);

		return view;
	}

	@Override
	public void onDDLFormLoaded(Record record) {

	}

	@Override
	public void onDDLFormRecordLoaded(Record record) {

	}

	@Override
	public void onDDLFormRecordAdded(Record record) {
		((MenuActivity) getActivity()).inflateFragmentAtPosition(0);
	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {
		((MenuActivity) getActivity()).inflateFragmentAtPosition(0);
	}

	@Override
	public void onDDLFormLoadFailed(Exception e) {

	}

	@Override
	public void onDDLFormRecordLoadFailed(Exception e) {

	}

	@Override
	public void onDDLFormRecordAddFailed(Exception e) {

	}

	@Override
	public void onDDLFormUpdateRecordFailed(Exception e) {

	}

	@Override
	public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {

	}

	@Override
	public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {

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
