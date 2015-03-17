package com.weiqun.newcool;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.weiqun.newcool.db.NewCoolDB;
import com.weiqun.newcool.util.MyUtils;
import com.weiqun.newcool.util.MyUtils.NetReportContract;

public class ChooseCityActivity extends Activity implements NetReportContract{

	private MyUtils utils;
	private ProgressDialog progressDialog;
	private Button btn1;

	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what) {
			case 0 :
				success();
				break;
			}
		}
		
	};
	private ArrayList<String> cityNames;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private NewCoolDB db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_city);
		btn1 = (Button) findViewById(R.id.btn1);
		utils = MyUtils.getInstance(this, this,handler);
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		listView = (ListView) findViewById(R.id.listView);
		cityNames = new ArrayList<String>();
		db = NewCoolDB.getInstance(this);
		cityNames.add("aaaa");
		cityNames.add("bbbb");
		cityNames.add("cccc");
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,cityNames);  
		listView.setAdapter(adapter);
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getCityNames();

//				Thread t = new Thread(new Runnable() {
//					
//					@Override
//					public void run() {
//						while(true){
//							try {
//								Thread.sleep(1000);
//								Log.i("weiqun12345", "current ThreadID=" + Thread.currentThread());
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
//						}
//						
//					}
//				});
//				t.start();
				
			}
		});
		

	}

	private void getCityNames() {
		progressDialog.show();
		if(db.getCitys() != null && db.getCitys().size()!=0) {
			cityNames.clear();
			cityNames.addAll(db.getCitys());
			Log.i("weiqun12345", "cityNames.size=" + cityNames.size());
			adapter.notifyDataSetChanged();
			if(progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				btn1.setText("success");
			}
		} else {
			utils.getCityListFromServer();
		}
		
		
	}
	
	@Override
	public void success() {
		getCityNames();
		Log.i("weiqun12345", "succ");
	}

	@Override
	public void failed() {
		Log.i("weiqun12345","failed");
	}
	
	
	
}
