package com.liferay.ldxdemo.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.ldxdemo.R;
import com.liferay.ldxdemo.activities.MenuActivity;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONObject;


public class ReviewFragment extends NamedFragment implements DDLFormListener {

	public static ReviewFragment newInstance() {
		return new ReviewFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.content_review, container, false);

		SessionContext.createBasicSession("test", "test");

		DDLFormScreenlet ddlFormScreenlet = (DDLFormScreenlet) view.findViewById(R.id.form_review);
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
		SessionContext.createBasicSession(getString(R.string.default_user), getString(R.string.default_password));

		goToCategory();

		AlertDialog.Builder builder =
				new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogCustom))
						.setTitle(R.string.sorry_for_the_experience)
						.setMessage(R.string.we_will_get_in_contact)
						.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
		builder.create().show();
	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {
		goToCategory();
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

	private void goToCategory() {
		((MenuActivity) getActivity()).inflateFragmentAtPosition(R.id.category);
	}

	@Override
	public String getName() {
		return "Review";
	}
}
