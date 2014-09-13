package kr.applepi.copyrightbaseballv2.vs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import kr.applepi.copyrightbaseballv2.R;
import kr.applepi.copyrightbaseballv2.main.MainActivity;
import kr.applepi.copyrightbaseballv2.single.SingleGameActivity;

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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class VsGameActivity extends Activity implements OnClickListener {
	final String link = "http://copyright.applepi.kr/selectQuiz.php";

	TextView tvQuestion;

	Button btnO;
	Button btnX;

	ImageView[] team1Base = new ImageView[4];
	ImageView[] team2Base = new ImageView[4];

	String[] question = new String[10];
	String[] answer = new String[10];

	int[] team1BaseId;
	int[] team2BaseId;
	int[] horseId;

	Drawable team1Horse;
	Drawable team2Horse;

	int questionIndex;
	int teamFlag;

	Handler mHandler;

	Team team1;
	Team team2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_vs_game);

		initUi();
		new GetJsonValue().execute();
	}

	void initUi() {
		tvQuestion = (TextView) findViewById(R.id.TV_VS_QUESTION);
		btnO = (Button) findViewById(R.id.BTN_VS_O);
		btnX = (Button) findViewById(R.id.BTN_VS_X);
		btnO.setOnClickListener(this);
		btnX.setOnClickListener(this);

		team1BaseId = new int[] { R.id.IMG_VS_1_HOME, R.id.IMG_VS_1_FIRST_BASE,
				R.id.IMG_VS_1_SECOND_BASE, R.id.IMG_VS_1_THIRD_BASE };
		team2BaseId = new int[] { R.id.IMG_VS_2_HOME, R.id.IMG_VS_2_FIRST_BASE,
				R.id.IMG_VS_2_SECOND_BASE, R.id.IMG_VS_2_THIRD_BASE };
		horseId = new int[] { R.drawable.horse1, R.drawable.horse2,
				R.drawable.horse3, R.drawable.horse4 };
		for (int i = 0; i < 4; i++) {
			team1Base[i] = (ImageView) findViewById(team1BaseId[i]);
			team2Base[i] = (ImageView) findViewById(team2BaseId[i]);
		}
		team1 = new Team();
		team2 = new Team();

		Intent mIntent = getIntent();
		team1.horseIndex = mIntent.getIntExtra("team1HorseIndex", 0);
		team2.horseIndex = mIntent.getIntExtra("team2HorseIndex", 0);

		team1.horseId = horseId[team1.horseIndex];
		team2.horseId = horseId[team2.horseIndex];

		team1.horseIndex = 0;
		team2.horseIndex = 0;

		team1.name = mIntent.getStringExtra("team1Name");
		team2.name = mIntent.getStringExtra("team2Name");

		mHandler = new Handler();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.BTN_VS_O:
			if (teamFlag % 2 == 0) {
				team1.answerFlag = 1;
				teamFlag++;
			} else {
				team2.answerFlag = 1;
				teamFlag++;
				checkAnswer();
			}
			break;
		case R.id.BTN_VS_X:
			if (teamFlag % 2 == 0) {
				team1.answerFlag = 0;
				teamFlag++;
			} else {
				team2.answerFlag = 0;
				teamFlag++;
				checkAnswer();
			}
			break;
		case R.id.btn_vs_main:
			Intent mIntent = new Intent(VsGameActivity.this, MainActivity.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(mIntent);
			finish();
			break;
		case R.id.btn_vs_replay:
			finish();
		}
	}

	void checkAnswer() {
		btnO.setEnabled(false);
		btnX.setEnabled(false);
		int tempAnswer = Integer.parseInt(answer[questionIndex]);
		if (tempAnswer == team1.answerFlag && tempAnswer == team2.answerFlag) {

			// team1차례
			team1.horseIndex++;
			team2.horseIndex++;
			team1.score += (int) (Math.random() * 3) + 1;
			team2.score += (int) (Math.random() * 3) + 1;
			tvQuestion.setTextSize(20);
			tvQuestion.setText("Perfect!\n" + team1.name + " " + team1.score
					+ "점 " + team1.wrongCount + " OUT!\n" + team2.name + " "
					+ team2.score + "점 " + +team2.wrongCount + " OUT!");

			team1RunHorse(team1.horseIndex);
			team2RunHorse(team2.horseIndex);
			mHandler.postDelayed(new AnswerRunnable(), 1000);
		} else if (tempAnswer == team1.answerFlag) {
			team1.horseIndex++;
			team1.score += (int) (Math.random() * 3) + 1;
			team2.wrongCount++;
			tvQuestion.setTextSize(20);
			tvQuestion.setText(team1.name + " 정답!\n" + team1.name + " "
					+ team1.score + "점 " + team1.wrongCount + " OUT!\n"
					+ team2.name + " " + team2.score + "점 " + +team2.wrongCount
					+ " OUT!");
			team1RunHorse(team1.horseIndex);
			mHandler.postDelayed(new AnswerRunnable(), 1000);
		} else if (tempAnswer == team2.answerFlag) {
			team2.horseIndex++;
			team2.score += (int) (Math.random() * 3) + 1;
			team1.wrongCount++;

			tvQuestion.setTextSize(20);
			tvQuestion.setText(team2.name + " 정답!\n" + team1.name + " "
					+ team1.score + "점 " + team1.wrongCount + " OUT!\n"
					+ team2.name + " " + team2.score + "점 " + +team2.wrongCount
					+ " OUT!");
			team2RunHorse(team2.horseIndex);
			mHandler.postDelayed(new AnswerRunnable(), 1000);
		} else {
			team1.wrongCount++;
			team2.wrongCount++;
			tvQuestion.setTextSize(20);
			tvQuestion.setText("모두 오답 \n" + team1.name + " " + team1.score
					+ "점 " + team1.wrongCount + " OUT!\n" + team2.name + " "
					+ team2.score + "점 " + +team2.wrongCount + " OUT!");
			mHandler.postDelayed(new AnswerRunnable(), 1000);
		}
	}

	void team1RunHorse(int horseIndex) {
		switch (horseIndex) {
		case 1:
			team1Base[1].setBackgroundResource(team1.horseId);
			break;
		case 2:
			team1Base[2].setBackgroundResource(team1.horseId);
			break;
		case 3:
			team1Base[3].setBackgroundResource(team1.horseId);
			break;
		case 7:
			team1Base[0].setBackgroundResource(android.R.color.transparent);
			team1Base[1].setBackgroundResource(team1.horseId);
			team1Base[2].setBackgroundResource(team1.horseId);
			team1Base[3].setBackgroundResource(team1.horseId);
			break;

		case 8:
			team1Base[0].setBackgroundResource(android.R.color.transparent);
			team1Base[1].setBackgroundResource(android.R.color.transparent);
			team1Base[2].setBackgroundResource(team1.horseId);
			team1Base[3].setBackgroundResource(team1.horseId);
			break;

		case 9:
			team1Base[0].setBackgroundResource(android.R.color.transparent);
			team1Base[1].setBackgroundResource(android.R.color.transparent);
			team1Base[2].setBackgroundResource(android.R.color.transparent);
			team1Base[3].setBackgroundResource(team1.horseId);
			break;
		case 10:
			team1Base[0].setBackgroundResource(android.R.color.transparent);
			team1Base[1].setBackgroundResource(android.R.color.transparent);
			team1Base[2].setBackgroundResource(android.R.color.transparent);
			team1Base[3].setBackgroundResource(android.R.color.transparent);
			break;
		}

	}

	void team2RunHorse(int horseIndex) {
		switch (horseIndex) {
		case 1:
			team2Base[1].setBackgroundResource(team2.horseId);
			break;
		case 2:
			team2Base[2].setBackgroundResource(team2.horseId);
			break;
		case 3:
			team2Base[3].setBackgroundResource(team2.horseId);
			break;
		case 7:
			team2Base[0].setBackgroundResource(android.R.color.transparent);
			team2Base[1].setBackgroundResource(team2.horseId);
			team2Base[2].setBackgroundResource(team2.horseId);
			team2Base[3].setBackgroundResource(team2.horseId);
			break;

		case 8:
			team2Base[0].setBackgroundResource(android.R.color.transparent);
			team2Base[1].setBackgroundResource(android.R.color.transparent);
			team2Base[2].setBackgroundResource(team2.horseId);
			team2Base[3].setBackgroundResource(team2.horseId);
			break;

		case 9:
			team2Base[0].setBackgroundResource(android.R.color.transparent);
			team2Base[1].setBackgroundResource(android.R.color.transparent);
			team2Base[2].setBackgroundResource(android.R.color.transparent);
			team2Base[3].setBackgroundResource(team2.horseId);
			break;
		case 10:
			team2Base[0].setBackgroundResource(android.R.color.transparent);
			team2Base[1].setBackgroundResource(android.R.color.transparent);
			team2Base[2].setBackgroundResource(android.R.color.transparent);
			team2Base[3].setBackgroundResource(android.R.color.transparent);
			break;
		}
	}

	class GetJsonValue extends AsyncTask<Void, Void, Void> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(VsGameActivity.this);

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
			team1Base[0].setBackgroundResource(team1.horseId);
			team2Base[0].setBackgroundResource(team2.horseId);
		}
	}

	class AnswerRunnable implements Runnable {

		@Override
		public void run() {
			if (team1.wrongCount == 3 || team2.wrongCount == 3
					|| questionIndex == question.length) {
				if (team1.score > team2.score) {
					createDialog(team1.name, team1.score, team2.name,
							team2.score);
				} else if (team1.score < team2.score) {
					createDialog(team2.name, team2.score, team1.name,
							team1.score);
				} else {
					showDrawDialog();
				}
			} else {
				btnO.setEnabled(true);
				btnX.setEnabled(true);
				tvQuestion.setTextSize(13);
				tvQuestion.setText(question[questionIndex]);
			}
		}

	}

	void createDialog(String winTeamName, int winScore, String loseTeamName,
			int loseScore) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				VsGameActivity.this);
		Context context = VsGameActivity.this;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		ViewGroup parent = (ViewGroup) findViewById(R.id.dialog_vs_result);

		View layout = inflater.inflate(R.layout.dialog_vs_result, parent);
		
		TextView tvTeam1 = (TextView) layout.findViewById(R.id.tv_vs_team1);
		TextView tvTeam2 = (TextView) layout.findViewById(R.id.tv_vs_team2);
		
		Button btnMain = (Button) layout.findViewById(R.id.btn_vs_main);
		Button btnReplay = (Button) layout.findViewById(R.id.btn_vs_replay);
		
		tvTeam1.setText(winTeamName + " " + winScore);
		tvTeam2.setText(loseTeamName + " " + loseScore);
		
		btnMain.setOnClickListener(this);
		btnReplay.setOnClickListener(this);
		
		builder.setView(layout);
		builder.show();
	}

	void showDrawDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				VsGameActivity.this);
		builder.setMessage("무승부 입니다");
		builder.setNeutralButton("메인으로 돌아가기",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
		builder.show();
	}
}

class Team {
	String name;
	int horseIndex;
	int horseId;
	int score;
	int wrongCount;
	int answerFlag;
}