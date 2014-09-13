package kr.applepi.copyrightbaseballv2.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import kr.applepi.copyrightbaseballv2.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	Button btnStartGame;
	Button btnAboutSites;
	Button btnStudy;
	Button btnRank;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		initUI();
		allocBtn();
	}

	void initUI() {
		if(! isOnline()) {
			new AlertDialog.Builder(this).setMessage("데이터 네트워크 또는 무선 네트워크를 켜주세요").setNeutralButton("종료", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			}).setCancelable(false).show();
		}
		
		btnStartGame = (Button) findViewById(R.id.BTN_START_GAME);
		btnAboutSites = (Button) findViewById(R.id.BTN_ABOUT_SITES);
		btnStudy = (Button) findViewById(R.id.BTN_STUDY);
		btnRank = (Button) findViewById(R.id.BTN_RANK);
		
	}

	void allocBtn() {
		Button[] arrBtn = { btnStartGame, btnAboutSites, btnStudy, btnRank };
		for (Button btn : arrBtn) {
			btn.setOnClickListener(this);
		}
	}

	private boolean isOnline() { // network 연결 상태 확인
		try {
			ConnectivityManager conMan = (ConnectivityManager) MainActivity.this
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			State wifi = conMan.getNetworkInfo(1).getState(); // wifi
			if (wifi == NetworkInfo.State.CONNECTED
					|| wifi == NetworkInfo.State.CONNECTING) {
				return true;
			}

			State mobile = conMan.getNetworkInfo(0).getState(); // mobile
																// ConnectivityManager.TYPE_MOBILE
			if (mobile == NetworkInfo.State.CONNECTED
					|| mobile == NetworkInfo.State.CONNECTING) {
				return true;
			}

		} catch (NullPointerException e) {
			return false;
		}

		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.BTN_START_GAME:
			startActivity(new Intent(MainActivity.this,
					SelectModeActivity.class));
			break;
		case R.id.BTN_ABOUT_SITES:
			startActivity(new Intent(MainActivity.this,
					AboutSitesActivity.class));
			break;
		case R.id.BTN_STUDY:
			startActivity(new Intent(MainActivity.this,
					AboutStudyActivity.class));
			break;
		case R.id.BTN_RANK:
			new getJsonValue().execute();
		}
	}

	class getJsonValue extends AsyncTask<Void, Void, Void> {

		TextView[] tvTeamName = new TextView[3];
		TextView[] tvTeamScore = new TextView[3];

		String[] teamName;
		String[] score;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			teamName = new String[3];
			score = new String[3];
			for (int i = 0; i < 3; i++) {
				teamName[i] = new String();
				score[i] = new String();
			}

		}

		protected Void doInBackground(Void... params) {
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://copyright.applepi.kr/selectRanking.php");
				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "utf-8"));
				StringBuilder sb = new StringBuilder();
				String line = null;

				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}

				is.close();

				String result = sb.toString();
				Log.d("result", result);
				JSONArray jsonArray = new JSONArray(result);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					teamName[i] = jsonObj.getString("teamName");
					score[i] = jsonObj.getString("score");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Context context = MainActivity.this;
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			ViewGroup parent = (ViewGroup) findViewById(R.id.dialog_rank);

			View layout = inflater.inflate(R.layout.dialog_rank, parent);

			tvTeamName[0] = (TextView) layout.findViewById(R.id.tv_team_name_1);
			tvTeamName[1] = (TextView) layout.findViewById(R.id.tv_team_name_2);
			tvTeamName[2] = (TextView) layout.findViewById(R.id.tv_team_name_3);

			tvTeamScore[0] = (TextView) layout
					.findViewById(R.id.tv_team_score_1);
			tvTeamScore[1] = (TextView) layout
					.findViewById(R.id.tv_team_score_2);
			tvTeamScore[2] = (TextView) layout
					.findViewById(R.id.tv_team_score_3);

			for (int i = 0; i < 3; i++) {
				tvTeamName[i].setText("" + teamName[i]);
				tvTeamScore[i].setText("" + score[i]);
			}
			builder.setView(layout);
			builder.show();
		}
	}

}
