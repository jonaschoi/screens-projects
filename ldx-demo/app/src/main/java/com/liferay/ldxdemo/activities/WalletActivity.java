package com.liferay.ldxdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.ldxdemo.R;
import com.liferay.mobile.android.callback.typed.JSONObjectCallback;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.ddlrecordset.DDLRecordSetService;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class WalletActivity extends Fragment implements BaseListListener<Record> {

	public static Fragment newInstance() {
		return new WalletActivity();
	}

//		setTitle(R.string.title_section2);


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_wallet, container, false);

		DDLListScreenlet ddlList = (DDLListScreenlet) view.findViewById(R.id.wallet_default);
		ddlList.setListener(this);
		return view;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!SessionContext.hasSession()) {
			startActivity(new Intent(getActivity(), MainActivity.class));
		}

		new LiferayCrouton.Builder().withInfoColor(R.color.material_primary_crouton).build();
	}

	@Override
	public void onListPageFailed(BaseListScreenlet source, int page, Exception e) {

	}

	@Override
	public void onListPageReceived(BaseListScreenlet source, int page, List<Record> entries, int rowCount) {

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
					getFragmentManager().
							beginTransaction().
							replace(R.id.content_frame, CouponActivity.newInstance(recordId, recordSetId, result.getInt("DDMStructureId"))).
							addToBackStack(null).
							commit();

					Crouton.clearCroutonsForActivity(getActivity());
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
	public void loadingFromCache(boolean success) {

	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {

	}

	@Override
	public void storingToCache(Object object) {

	}
}
