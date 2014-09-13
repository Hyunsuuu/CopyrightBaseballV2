package kr.applepi.copyrightbaseballv2.single;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import kr.applepi.copyrightbaseballv2.R;
import kr.applepi.copyrightbaseballv2.main.MainActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SingleGameActivity extends Activity implements OnClickListener {
	final String link = "http://copyright.applepi.kr/selectQuiz.php";

	SharedPreferences pref;
	SharedPreferences.Editor editor;

	TextView tvQuestion;

	Button btnO;
	Button btnX;

	ImageView[] base = new ImageView[4];

	Handler mHandler;

	String teamName;

	String[] question = new String[10];
	String[] answer = new String[10];

	int answerFlag = 0;
	int questionIndex = 0;
	int wrongCount = 0;
	int highScore = 0;

	int horseIndex = 0;

	int[] baseId;
	int[] horseId;

	int score;
	
	boolean rankFlag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_single_game);

		initUI();
		allocBtn();
		new getJsonValue().execute();
	}

	void initUI() {
		pref = getSharedPreferences("score", MODE_PRIVATE);
		editor = pref.edit();
		highScore = pref.getInt("highScore", 0);

		tvQuestion = (TextView) findViewById(R.id.TV_SINGLE_QUESTION);

		btnO = (Button) findViewById(R.id.BTN_SINGLE_O);
		btnX = (Button) findViewById(R.id.BTN_SINGLE_X);

		baseId = new int[] { R.id.IMG_SINGLE_HOME, R.id.IMG_SINGLE_FIRST_BASE,
				R.id.IMG_SINGLE_SECOND_BASE, R.id.IMG_SINGLE_THIRD_BASE };
		horseId = new int[] { R.drawable.horse1, R.drawable.horse2,
				R.drawable.horse3, R.drawable.horse4 };

		for (int i = 0; i < 4; i++) {
			base[i] = (ImageView) findViewById(baseId[i]);
		}

		mHandler = new Handler();
		Intent mIntent = getIntent();
		teamName = mIntent.getStringExtra("teamName");
		runHorse();
	}

	void allocBtn() {
		Button[] btn = { btnO, btnX };
		for (Button b : btn) {
			b.setOnClickListener(this);
		}
	}

	void checkAnswer() {
		btnO.setEnabled(false);
		btnX.setEnabled(false);
		if (Integer.parseInt(answer[questionIndex]) == answerFlag) {
			questionIndex++;
			horseIndex++;
			score += (int) (Math.random() * 3) + 1;
			tvQuestion.setTextSize(20);
			tvQuestion.setText("정답입니다 / 총 " + score + "점");

			mHandler.postDelayed(new AnswerRunnable(), 1000);
			runHorse();
		} else {
			questionIndex++;
			wrongCount++;
			tvQuestion.setTextSize(20);
			tvQuestion.setText("틀렸습니다 / 총 " + wrongCount + "OUT");

			mHandler.postDelayed(new AnswerRunnable(), 1000);
		}
	}

	void runHorse() {
		switch (horseIndex) {
		case 1:
			base[0].setBackgroundResource(horseId[1]);
			base[1].setBackgroundResource(horseId[0]);
			break;
		case 2:
			base[0].setBackgroundResource(horseId[2]);
			base[1].setBackgroundResource(horseId[1]);
			base[2].setBackgroundResource(horseId[0]);

			break;
		case 3:
			base[0].setBackgroundResource(horseId[3]);
			base[1].setBackgroundResource(horseId[2]);
			base[2].setBackgroundResource(horseId[1]);
			base[3].setBackgroundResource(horseId[0]);
			break;
		case 4:
			base[0].setBackgroundResource(horseId[0]);
			base[1].setBackgroundResource(horseId[3]);
			base[2].setBackgroundResource(horseId[2]);
			base[3].setBackgroundResource(horseId[1]);
			break;
		case 5:
			base[0].setBackgroundResource(horseId[1]);
			base[1].setBackgroundResource(horseId[0]);
			base[2].setBackgroundResource(horseId[3]);
			base[3].setBackgroundResource(horseId[2]);
			break;
		case 6:
			base[0].setBackgroundResource(horseId[2]);
			base[1].setBackgroundResource(horseId[1]);
			base[2].setBackgroundResource(horseId[0]);
			base[3].setBackgroundResource(horseId[3]);

			break;

		case 7:
			base[0].setBackgroundResource(android.R.color.transparent);
			base[1].setBackgroundResource(horseId[2]);
			base[2].setBackgroundResource(horseId[1]);
			base[3].setBackgroundResource(horseId[0]);
			break;

		case 8:
			base[0].setBackgroundResource(android.R.color.transparent);
			base[1].setBackgroundResource(android.R.color.transparent);
			base[2].setBackgroundResource(horseId[2]);
			base[3].setBackgroundResource(horseId[1]);
			break;

		case 9:
			base[0].setBackgroundResource(android.R.color.transparent);
			base[1].setBackgroundResource(android.R.color.transparent);
			base[2].setBackgroundResource(android.R.color.transparent);
			base[3].setBackgroundResource(horseId[2]);
			break;

		case 10:
			base[0].setBackgroundResource(android.R.color.transparent);
			base[1].setBackgroundResource(android.R.color.transparent);
			base[2].setBackgroundResource(android.R.color.transparent);
			base[3].setBackgroundResource(android.R.color.transparent);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.BTN_SINGLE_O:
			answerFlag = 1;
			checkAnswer();
			break;
		case R.id.BTN_SINGLE_X:
			answerFlag = 0;
			checkAnswer();
			break;
		case R.id.btn_single_confirm_high_score:
			if(rankFlag) {
				new InsertRanking().execute();
			} else {
				Toast.makeText(SingleGameActivity.this, "이미 랭킹을 등록했습니다", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_single_high_score_main:
		case R.id.btn_single_main:
			Intent mIntent = new Intent(SingleGameActivity.this, MainActivity.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(mIntent);
			finish();
			break;
		case R.id.btn_single_high_score_replay:
		case R.id.btn_single_replay:
			finish();
			break;
		}
	}

	class AnswerRunnable implements Runnable {

		@Override
		public void run() {
			if (wrongCount == 3 || questionIndex == question.length) {

				if (score >= highScore) {
					editor.putString("teamName", teamName);
					editor.putInt("highScore", score);
					editor.commit();
					Log.i("run, highScore", pref.getInt("highScore", 0) + "");
					createDialog(R.id.dialog_single_high_score,
							R.layout.dialog_single_high_score);
				} else {
					createDialog(R.id.dialog_single_result, R.layout.dialog_single_result);
				}
			} else {
				btnO.setEnabled(true);
				btnX.setEnabled(true);
				tvQuestion.setTextSize(13);
				tvQuestion.setText(question[questionIndex]);
				
			}
		}

	}

	void createDialog(int id, int resource) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				SingleGameActivity.this);
		Context context = SingleGameActivity.this;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		ViewGroup parent = (ViewGroup) findViewById(id);

		View layout = inflater.inflate(resource, parent);

		if (layout.getId() == R.id.dialog_single_high_score) {
			TextView tvHighScore = (TextView) layout
					.findViewById(R.id.tv_single_high_score);
			Button btnConfirm = (Button) layout
					.findViewById(R.id.btn_single_confirm_high_score);
			Button btnMain = (Button) layout
					.findViewById(R.id.btn_single_high_score_main);
			Button btnReplay = (Button) layout
					.findViewById(R.id.btn_single_high_score_replay);
			
			tvHighScore.setText("" + pref.getInt("highScore", 0));
			btnConfirm.setOnClickListener(this);
			btnMain.setOnClickListener(this);
			btnReplay.setOnClickListener(this);

		} else if (layout.getId() == R.id.dialog_single_result) {
			TextView tvScore = (TextView) layout.findViewById(R.id.tv_single_score);
			Button btnMain = (Button) layout.findViewById(R.id.btn_single_main);
			Button btnReplay = (Button) layout.findViewById(R.id.btn_single_replay);
			
			tvScore.setText("" + score);
			btnMain.setOnClickListener(this);
			btnReplay.setOnClickListener(this);
		}
		builder.setView(layout);
		builder.setCancelable(false);
		builder.show();
	}

	class getJsonValue extends AsyncTask<Void, Void, Void> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(SingleGameActivity.this);

			progressDialog.setMessage("잠시만 기다려주세요..");
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(link);
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
				Log.i("result", result);
				JSONArray jsonArray = new JSONArray(result);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					question[i] = jsonObj.getString("question");
					answer[i] = jsonObj.getString("answer");
					Log.i("문제", answer[i]);
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
			progressDialog.dismiss();
			tvQuestion.setText(question[questionIndex]);
			base[0].setBackgroundResource(horseId[0]);
		}
	}

	class InsertRanking extends AsyncTask<Void, Void, Void> {
		HttpClient hc = new DefaultHttpClient();
		HttpPost hp = new HttpPost(
				"http://copyright.applepi.kr/insertRanking.php");

		@Override
		protected Void doInBackground(Void... params) {
			try {
				ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("teamName", pref.getString(
						"teamName", "")));
				list.add(new BasicNameValuePair("score", Integer
						.toString(score)));
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,
						"UTF-8");
				hp.setEntity(entity);
				HttpResponse response = hc.execute(hp);

				String text = EntityUtils.toString(response.getEntity());
				Log.d("entity", EntityUtils.toString(entity));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			rankFlag = false;
			Toast.makeText(SingleGameActivity.this, "등록 되었습니다", 1000).show();

		}
	}
}