package com.liferay.dxpdemo.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liferay.dxpdemo.BuildConfig;
import com.liferay.dxpdemo.R;
import com.liferay.mobile.android.v7.expandovalue.ExpandoValueService;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoginListener, View.OnClickListener {

	public static final String EXPANDO_DATA = "EXPANDO_DATA";
	public static final String FINANCIAL_DATA = "FINANCIAL_DATA";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LoginScreenlet loginScreenlet = (LoginScreenlet) findViewById(R.id.login_screenlet);
		loginScreenlet.setListener(this);

		if (BuildConfig.DEBUG) {
//			TextView login = (TextView) loginScreenlet.findViewById(R.id.liferay_login);
//			login.setText(R.string.default_user);
//			TextView password = (TextView) loginScreenlet.findViewById(R.id.liferay_password);
//			password.setText(R.string.default_password);
		}
	}

	@Override
	public void onLoginSuccess(final User user) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				ExpandoValueService expandoColumnService = new ExpandoValueService(SessionContext.createSessionFromCurrentSession());
				String userClassName = "com.liferay.portal.kernel.model.User";
				String tableName = "CUSTOM_FIELDS";
				String columnName = "Financialdata";

				SharedPreferences sharedPreferences = getSharedPreferences(EXPANDO_DATA, MODE_PRIVATE);

				try {
					JSONObject jsonObject = expandoColumnService.getJsonData(LiferayServerContext.getCompanyId(), userClassName, tableName, columnName, user.getId());

					sharedPreferences.edit().putString(FINANCIAL_DATA, jsonObject.toString()).apply();

					LiferayLogger.d(String.valueOf(jsonObject.keys()));
				} catch (Exception e) {

					sharedPreferences.edit().remove(FINANCIAL_DATA);
					LiferayLogger.e("Could not load expando data", e);
				}

				startActivity(new Intent(MainActivity.this, MenuActivity.class));
			}
		}).start();
	}

	@Override
	public void onLoginFailure(Exception e) {
		View content = findViewById(android.R.id.content);
		Snackbar.make(content, "Error logging in", Snackbar.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(this, SignUpActivity.class));
	}
}
