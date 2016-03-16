package com.liferay.dxpdemo.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.liferay.dxpdemo.R;
import com.liferay.dxpdemo.activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public class BalanceFragment extends Fragment {

	public static BalanceFragment newInstance() {
		Bundle args = new Bundle();

		BalanceFragment fragment = new BalanceFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_balance, container, false);

		renderGraph(view);

		return view;
	}

	private void renderGraph(View view) {

		SharedPreferences sharedPreferences =
				getActivity().getSharedPreferences(MainActivity.EXPANDO_DATA, Context.MODE_PRIVATE);
		String financialData = sharedPreferences.getString(MainActivity.FINANCIAL_DATA, "");

		try {
			JSONObject jsonObject = new JSONObject(financialData);
			JSONObject historyChart = ((JSONObject) ((JSONArray) jsonObject.get("accounts")).get(0));
			JSONArray history = (JSONArray) historyChart.get("history");

			BarChart barChart = (BarChart) view.findViewById(R.id.chart1);
			BarData data = getBarData(history);
			barChart.setData(data);
			barChart.setDescription(historyChart.getString("name"));
			barChart.setDrawBarShadow(false);
			barChart.setDrawGridBackground(false);

			XAxis xAxis = barChart.getXAxis();
			xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
			xAxis.setDrawGridLines(false);
			xAxis.setSpaceBetweenLabels(2);

			YAxis leftAxis = barChart.getAxisLeft();
			leftAxis.setEnabled(false);

			YAxis rightAxis = barChart.getAxisRight();
			rightAxis.setEnabled(false);

			Legend legend = barChart.getLegend();
			legend.setEnabled(false);
			legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
			legend.setForm(Legend.LegendForm.SQUARE);

			barChart.animateXY(1000, 1000);
			barChart.invalidate();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@NonNull
	private BarData getBarData(JSONArray historyValues) throws JSONException {

		ArrayList<BarEntry> values = new ArrayList<>();
		List<String> xAxis = new ArrayList<>();

		for (int i = 0; i < historyValues.length(); i++) {
			JSONObject jsonObject = historyValues.getJSONObject(i);

			values.add(new BarEntry(jsonObject.getInt("value"), i));
			xAxis.add(jsonObject.getString("date"));
		}

		BarDataSet dataSet = new BarDataSet(values, "");
		dataSet.setColor(Color.rgb(217, 80, 138));
//		dataSet.setColors(ColorTemplate.PASTEL_COLORS);

		List<IBarDataSet> dataSets = new ArrayList<>();
		dataSets.add(dataSet);


		return new BarData(xAxis, dataSets);
	}

}
