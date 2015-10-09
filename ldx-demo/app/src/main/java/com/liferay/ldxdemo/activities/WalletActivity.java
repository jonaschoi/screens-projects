package com.liferay.ldxdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.task.callback.typed.JSONObjectAsyncTaskCallback;
import com.liferay.mobile.android.v62.ddlrecordset.DDLRecordSetService;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.push.AbstractPushActivity;

import com.liferay.ldxdemo.R;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;

/**
 * @author dipenp
 *
 */
public class WalletActivity extends AbstractPushActivity implements BaseListListener<DDLEntry> {

	private DDLListScreenlet ddlList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wallet);

//		/**
//		 * Adding our layout to parent class frame layout.
//		 */
//		getLayoutInflater().inflate(R.layout.activity_wallet, frameLayout);
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

	protected Session getDefaultSession() {
		return SessionContext.createSessionFromCurrentSession();
	}

	@Override
	public void onListPageReceived(BaseListScreenlet source, int page, List<DDLEntry> entries, int rowCount) {

	}


	@Override
	public void onListItemSelected(DDLEntry element, View view) {
		loadDDLForm(element);
	}

	private void loadDDLForm(DDLEntry element) {
		final Integer recordId = (Integer) (element.getAttributes("recordId"));
		final Integer recordSetId = (Integer) (element.getAttributes("recordSetId"));

		try {
			Session session = SessionContext.createSessionFromCurrentSession();
			session.setCallback(getCallback(recordId, recordSetId));

			new DDLRecordSetService(session).getRecordSet(recordSetId);
		} catch (Exception e) {
			LiferayLogger.e("error loading structure id", e);
		}
	}

	private JSONObjectAsyncTaskCallback getCallback(final Integer recordId, final Integer recordSetId) {
		return new JSONObjectAsyncTaskCallback() {

			@Override
			public void onSuccess(JSONObject result) {
				try {
					Intent intent = new Intent(WalletActivity.this, CouponActivity.class);
					intent.putExtra("recordId", recordId);
					intent.putExtra("recordSetId", recordSetId);
					intent.putExtra("structureId", result.getInt("DDMStructureId"));

					Crouton.clearCroutonsForActivity(WalletActivity.this);

					startActivity(intent);
				}
				catch (JSONException e) {
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
}
