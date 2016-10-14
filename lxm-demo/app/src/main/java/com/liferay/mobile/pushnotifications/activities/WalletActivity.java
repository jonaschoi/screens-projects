package com.liferay.mobile.pushnotifications.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.liferay.mobile.android.callback.typed.JSONObjectCallback;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.ddlrecordset.DDLRecordSetService;
import com.liferay.mobile.pushnotifications.R;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.push.PushScreensActivity;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class WalletActivity extends PushScreensActivity implements BaseListListener<Record> {

	private DDLListScreenlet ddlList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wallet);

		if (!SessionContext.isLoggedIn()) {
			startActivity(new Intent(this, LoginActivity.class));
		}

		ddlList = (DDLListScreenlet) findViewById(R.id.wallet_default);
		ddlList.setListener(this);
	}

	protected Session getDefaultSession() {
		return SessionContext.createSessionFromCurrentSession();
	}

	@Override
	public void onListPageFailed(int startRow, Exception e) {

	}

	@Override
	public void onListPageReceived(int startRow, int endRow, List<Record> entries, int rowCount) {

	}

	@Override
	public void onListItemSelected(Record element, View view) {
		loadDDLForm(element);
	}

	private void loadDDLForm(Record element) {
		final Integer recordId = (Integer) (element.getModelValues().get("recordId"));
		final Integer recordSetId = (Integer) (element.getModelValues().get("recordSetId"));

		try {
			Session session = SessionContext.createSessionFromCurrentSession();
			session.setCallback(getCallback(recordId, recordSetId));

			new DDLRecordSetService(session).getRecordSet(recordSetId);
		} catch (Exception e) {
			LiferayLogger.e("error loading structure id", e);
		}
	}

	private JSONObjectCallback getCallback(final Integer recordId, final Integer recordSetId) {
		return new JSONObjectCallback() {

			@Override
			public void onSuccess(JSONObject result) {
				try {
					Intent intent = new Intent(WalletActivity.this, CouponActivity.class);
					intent.putExtra("recordId", recordId);
					intent.putExtra("recordSetId", recordSetId);
					intent.putExtra("structureId", result.getInt("DDMStructureId"));

					startActivity(intent);
				} catch (JSONException e) {
					LiferayLogger.e("error parsing JSON", e);
				}
			}

			@Override
			public void onFailure(Exception e) {
				LiferayLogger.e("error loading structure id", e);
			}
		};
	}

	@Override
	protected void onPushNotificationReceived(final JSONObject jsonObject) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				LiferayLogger.i("Reloading list...");
				ddlList.loadPage(0);
			}
		});
	}

	@Override
	protected void onErrorRegisteringPush(final String message, final Exception e) {

	}

	@Override
	protected String getSenderId() {
		return getString(R.string.sender_id);
	}

	@Override
	public void error(Exception e, String userAction) {

	}
}
