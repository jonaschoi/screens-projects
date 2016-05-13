package com.liferay.ldxdemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.ldxdemo.R;
import com.liferay.ldxdemo.activities.MenuActivity;
import com.liferay.mobile.android.callback.typed.JSONObjectCallback;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.ddlrecordset.DDLRecordSetService;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * @author Javier Gamarra
 */
public class WalletFragment extends NamedFragment implements BaseListListener<Record> {

	public static WalletFragment newInstance() {
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
			SessionContext.loadStoredCredentials(CredentialsStorageBuilder.StorageType.SHARED_PREFERENCES);
			((MenuActivity) getActivity()).loadPortrait();
		}
	}

	@Override
	public void onListPageFailed(BaseListScreenlet source, int page, Exception e) {

	}

	@Override
	public void onListPageReceived(BaseListScreenlet source, int page, List<Record> entries, int rowCount) {

	}

	@Override
	public void onListItemSelected(Record element, View view) {

		//loadDDLForm(element);

		getFragmentManager().
						beginTransaction().
							replace(R.id.content_frame, CouponFragment.newInstance()).
							addToBackStack(null).
							commit();
	}

//	private void loadDDLForm(Record element) {
//		final String recordId = (String) (element.getModelAttributes().get("recordId"));
//		final String recordSetId = (String) (element.getModelAttributes().get("recordSetId"));
//
//		try {
//			Session session = SessionContext.createSessionFromCurrentSession();
//			session.setCallback(getCallback(recordId, recordSetId));
//
//			new DDLRecordSetService(session).getRecordSet(Long.valueOf(recordSetId));
//		} catch (Exception e) {
//			LiferayLogger.e("error loading structuare id", e);
//		}
//	}
//
//	private JSONObjectCallback getCallback(final String recordId, final String recordSetId) {
//		return new JSONObjectCallback() {
//
//			@Override
//			public void onSuccess(JSONObject result) {
//				try {
//					getFragmentManager().
//							beginTransaction().
//							replace(R.id.content_frame, CouponFragment.newInstance(recordId, recordSetId, result.getInt("DDMStructureId"))).
//							addToBackStack(null).
//							commit();
//
//				} catch (JSONException e) {
//					LiferayLogger.e("error parsing JSON", e);
//				}
//			}
//
//			@Override
//			public void onFailure(Exception e) {
//				LiferayLogger.e("error loading structure id", e);
//			}
//		};
//	}

	@Override
	public void loadingFromCache(boolean success) {

	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {

	}

	@Override
	public void storingToCache(Object object) {

	}

	@Override
	public String getName() {
		return "My Wallet";
	}
}
