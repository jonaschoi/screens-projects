package com.liferay.ldxdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.liferay.ldxdemo.R;
import com.liferay.mobile.android.callback.typed.JSONObjectCallback;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.ddlrecordset.DDLRecordSetService;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.push.AbstractPushActivity;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class WalletActivity extends AbstractPushActivity implements BaseListListener<Record> {

	private DDLListScreenlet ddlList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wallet);
		setTitle(R.string.title_section2);

//		/**
//		 * Adding our layout to parent class frame layout.
//		 */
//		getLayoutInflater().inflate(R.layout.activity_wallet, NavDrawerActivity.frameLayout);
//
//		/**
//		 * Setting title and itemChecked
//		 */
//		mDrawerList.setItemChecked(position, true);
//		setTitle(listArray[position]);

		if (!SessionContext.hasSession()) {
			startActivity(new Intent(this, MainActivity.class));
		}

		new LiferayCrouton.Builder().withInfoColor(R.color.material_primary_crouton).build();

		ddlList = (DDLListScreenlet) findViewById(R.id.wallet_default);
		ddlList.setListener(this);

	}

	@Override
	public void onListPageFailed(BaseListScreenlet source, int page, Exception e) {

	}

	@Override
	public void onListPageReceived(BaseListScreenlet source, int page, List<Record> entries, int rowCount) {

	}

	protected Session getDefaultSession() {
		return SessionContext.createSessionFromCurrentSession();
	}

	@Override
	public void onListItemSelected(Record element, View view) {
		loadDDLForm(element);
	}

	private void loadDDLForm(Record element) {
		final Integer recordId = (Integer) (element.getModelAttributes().get("recordId"));
		final Integer recordSetId = (Integer) (element.getModelAttributes().get("recordSetId"));

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

					Crouton.clearCroutonsForActivity(WalletActivity.this);

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
				Crouton.clearCroutonsForActivity(WalletActivity.this);
				LiferayCrouton.info(WalletActivity.this, "Reloading list...");
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
	public void loadingFromCache(boolean success) {

	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {

	}

	@Override
	public void storingToCache(Object object) {

	}
}
