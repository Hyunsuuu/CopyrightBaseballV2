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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleGameActivity extends Activity implements OnClickListener{
	final String link = "http://copyright.applepi.kr/selectQuiz.php";
	TextView tvQuestion;

	Button btnO;
	Button btnX;

	ImageView imgHome;
	ImageView imgFirstBase;
	ImageView imgSecondBase;
	ImageView imgThirdBase;

	String[] question = new String[8];
	String[] answer = new String[8];
	
	int index = 0;
	
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
		tvQuestion = (TextView) findViewById(R.id.TV_SINGLE_QUESTION);

		btnO = (Button) findViewById(R.id.BTN_SINGLE_O);
		btnX = (Button) findViewById(R.id.BTN_SINGLE_X);

		imgHome = (ImageView) findViewById(R.id.IMG_SINGLE_HOME);
		imgFirstBase = (ImageView) findViewById(R.id.IMG_SINGLE_FIRST_BASE);
		imgSecondBase = (ImageView) findViewById(R.id.IMG_SINGLE_SECOND_BASE);
		imgThirdBase = (ImageView) findViewById(R.id.IMG_SINGLE_THIRD_BASE);

	}
	void allocBtn() {
		Button[] btn = { btnO, btnX };
		for(Button b : btn) {
			b.setOnClickListener(this);
		}
	}
	class getJsonValue extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(link);
				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
				StringBuilder sb = new StringBuilder();
				String line = null;
				
				while( ( line = reader.readLine()) != null ) {
					sb.append(line + "\n");
				}
				is.close();
				
				String result = sb.toString();
				
				JSONArray jsonArray = new JSONArray(result);
				for(int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					question[i] = jsonObj.getString("question");
					answer[i] = jsonObj.getString("answer");
					Log.d("BBB", "야호  :" + jsonObj.getInt("no"));
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			text();
		}

	}
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.BTN_SINGLE_O :
			index++;
			text();
			break;
		case R.id.BTN_SINGLE_X :
			index++;
			text();
			break;
		}
	}
	
	void text() {
		tvQuestion.setText(question[index]);
	}
}