package kr.applepi.copyrightbaseballv2.single;

import kr.applepi.copyrightbaseballv2.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SingleAddTeamNameActivity extends Activity {

	Button btnTeamNameConfirm;
	EditText etTeamName;

	String teamName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_single_add_team_name);
		initUI();

		btnTeamNameConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (etTeamName.getText().toString().length() == 0) {
					Toast.makeText(SingleAddTeamNameActivity.this,
							"팀 이름을 작성해주세요", 1000).show();
				} else {
					teamName = etTeamName.getText().toString();
					Intent mIntent = new Intent(SingleAddTeamNameActivity.this,
							SingleGameActivity.class);
					mIntent.putExtra("teamName", teamName);
					startActivity(mIntent);
					finish();
				}
			}
		});
	}

	void initUI() {
		btnTeamNameConfirm = (Button) findViewById(R.id.BTN_SINGLE_NAME_CONFIRM);
		etTeamName = (EditText) findViewById(R.id.ET_SINGLE_NAME);
	}
}
