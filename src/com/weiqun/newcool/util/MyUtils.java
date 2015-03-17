package com.weiqun.newcool.util;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weiqun.newcool.db.NewCoolDB;
import com.weiqun.newcool.model.City;

public class MyUtils {

	private static MyUtils util;
	public interface NetReportContract {
		void success();

		void failed();
	}
	private Handler mHandler;
	private Context mContext;
	private RequestQueue mQueue;
	private NetReportContract mNetReportContract;

	private MyUtils(Context context, NetReportContract netReportContract,Handler handler) {
		mContext = context;
		mQueue = Volley.newRequestQueue(context);
		mNetReportContract = netReportContract;
		mHandler = handler;
	}

	public synchronized static MyUtils getInstance(Context context, NetReportContract netReportContract,Handler handler){
		if(util == null) {
			util = new MyUtils(context, netReportContract,handler);
		}
		return util;
	}
	
	public void getCityListFromServer() {
		
		String url = "http://v.juhe.cn/weather/citys?key=e847b72fcd11f2056d026d3b03959e8a&dtype=json";
		Request<JSONObject> request = new NormalPostRequest(url,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						parseCityList(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						mNetReportContract.failed();
					}
				}, null);
		mQueue.add(request);
	}

	class ParseCityListRunnable implements Runnable {
		
		private JSONObject result;
		public ParseCityListRunnable(JSONObject result) {
			this.result = result;
		}
		@Override
		public void run() {
			try {
				Log.i("weiqun12345", "error_code=" + result.getString("resultcode"));
				if ("200".equals(result.getString("resultcode"))) {
					String content = result.getString("result");
					Log.e("weiqun12345", "result=" + content);
					Gson gson = new Gson();
					ArrayList<City> cityList = gson.fromJson(content,new TypeToken<ArrayList<City>>() {}.getType());
					Log.e("weiqun12345", "newsList.size=" + cityList.size());
					NewCoolDB db = NewCoolDB.getInstance(mContext);
					db.saveCitys(cityList);
//					mNetReportContract.success();
					Message msg = mHandler.obtainMessage();
					msg.what = 0;
					mHandler.sendMessage(msg);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void parseCityList(JSONObject response) {
		Thread t = new Thread(new ParseCityListRunnable(response));
		t.start();
	}

	public void getWeatherFromServerBYCity(String cityName) {

	}

	public void getWeatherTypeFromServer() {

	}
}
