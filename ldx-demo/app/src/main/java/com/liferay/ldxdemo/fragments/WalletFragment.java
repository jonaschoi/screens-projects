package com.liferay.ldxdemo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.ldxdemo.R;
import com.liferay.ldxdemo.activities.FragmentLoaded;
import com.liferay.ldxdemo.activities.MainActivity;
import com.liferay.mobile.android.callback.typed.JSONObjectCallback;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.ddlrecordset.DDLRecordSetService;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public class WalletFragment extends AbstractWebContentFragment implements BaseListListener<Record> {

	public static Fragment newInstance() {
		return new WalletFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.content_wallet, container, false);

		DDLListScreenlet ddlList = (DDLListScreenlet) view.findViewById(R.id.wallet_default);
		ddlList.setListener(this);
		return view;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!SessionContext.isLoggedIn()) {
			startActivity(new Intent(getActivity(), MainActivity.class));
		}
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
							replace(R.id.content_frame, CouponFragment.newInstance(recordId, recordSetId, result.getInt("DDMStructureId"))).
							addToBackStack(null).
							commit();

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
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		FragmentLoaded activity = (FragmentLoaded) getActivity();
		activity.onFragmentLoaded(getView().findViewById(R.id.wallet_layout), true);
	}

	@Override
	public void error(Exception e, String userAction) {

	}
}
