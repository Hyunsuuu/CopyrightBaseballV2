package kr.applepi.copyrightbaseballv2.single;

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
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleGameActivity extends Activity implements OnClickListener {
	final String link = "http://copyright.applepi.kr/selectQuiz.php";

	SharedPreferences pref;
	SharedPreferences.Editor editor;

	TextView tvQuestion;

	Button btnO;
	Button btnX;

	ImageView[] base = new ImageView[4];
	Drawable[] horse = new Drawable[4];

	Handler mHandler;

	String teamName;

	String[] question = new String[8];
	String[] answer = new String[8];

	int answerFlag = 0;
	int questionIndex = 0;
	int correctionCount = 0;
	int wrongCount = 0;
	int highScore = 0;

	int horseIndex = 0;
	int baseIndex = 0;
	int tempIndex = 0;

	int[] baseId;
	int[] horseId;

	int testFlag = 1;
	boolean tempFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
			horse[i] = getResources().getDrawable(horseId[i]);
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

		if (Integer.parseInt(answer[questionIndex]) == answerFlag) {
			correctionCount++;
			tvQuestion.setText("정답입니다 / 총 " + correctionCount + "점");
			mHandler.postDelayed(new AnswerRunnable(), 700);
			baseIndex++;
			runHorse();
			questionIndex++;
		} else {
			wrongCount++;
			tvQuestion.setText("틀렸습니다 / 총 " + wrongCount + "OUT");
			mHandler.postDelayed(new AnswerRunnable(), 700);
			questionIndex++;
		}
	}

	void runHorse() {
		tempIndex = baseIndex;

		for (int i = 0; i <= baseIndex; i++) {

			if (tempIndex > 3 || tempFlag) {

				if (tempIndex >= baseIndex) {
					tempIndex -= 4;
				}

				if (tempIndex > 0) {
					tempFlag = true;
				} else {
					tempFlag = false;
				}

				base[i].setImageDrawable(horse[tempIndex]);
				Log.i("IF", "i : " + i + " / tempIndex : " + tempIndex);
				tempIndex--;
				testFlag = 0;

			} else {

				if (testFlag == 1) {
					horseIndex = baseIndex;
					testFlag = -1;
				} else if (testFlag == 0) {
					horseIndex = 3;
					testFlag = -1;
				}

				base[i].setImageDrawable(horse[horseIndex]);
				Log.i("ELSE", "i : " + i + " / horseIndex : " + horseIndex);
				horseIndex--;

			}
			if (i == 3) {
				break;
			}
		}
		testFlag = 1;
		tempFlag = false;
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
		}
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
		}
	}

	class AnswerRunnable implements Runnable {

		@Override
		public void run() {
			if (wrongCount == 3 || questionIndex == question.length) {

				if (correctionCount >= highScore) {
					editor.putString("teamName", teamName);
					editor.putInt("highScore", correctionCount);
					editor.commit();
				}
				new AlertDialog.Builder(SingleGameActivity.this)
						.setTitle("끝났습니다")
						.setMessage(teamName + " / 맞은 개수 : " + correctionCount)
						.setNeutralButton("야호",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										finish();
									}

								}).show();
			} else {
				tvQuestion.setText(question[questionIndex]);
			}
		}

	}
}