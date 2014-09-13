package kr.applepi.copyrightbaseballv2.vs;

import kr.applepi.copyrightbaseballv2.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VsAddTeamActivity extends Activity implements OnClickListener {

	Button btnTeamNameConfirm;

	Button[] btnTeam1Horse = new Button[4];
	Button[] btnTeam2Horse = new Button[4];

	EditText etTeam1Name;
	EditText etTeam2Name;

	String team1Name;
	String team2Name;
	
	int tempTeam1Index = 0;
	int tempTeam2Index = 0;

	int[] horseId = new int[4];
	int[] selectedHorseId = new int[4];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_vs_add_team);

		initUI();
	}

	void initUI() {
		btnTeamNameConfirm = (Button) findViewById(R.id.BTN_VS_NAME_CONFIRM);
		btnTeamNameConfirm.setOnClickListener(this);
		etTeam1Name = (EditText) findViewById(R.id.ET_VS_1_NAME);
		etTeam2Name = (EditText) findViewById(R.id.ET_VS_2_NAME);

		int btnTeam1Id[] = new int[] { R.id.BTN_VS_1_HORSE1,
				R.id.BTN_VS_1_HORSE2, R.id.BTN_VS_1_HORSE3,
				R.id.BTN_VS_1_HORSE4 };

		int btnTeam2Id[] = new int[] { R.id.BTN_VS_2_HORSE1,
				R.id.BTN_VS_2_HORSE2, R.id.BTN_VS_2_HORSE3,
				R.id.BTN_VS_2_HORSE4 };

		horseId = new int[] { R.drawable.horse1, R.drawable.horse2,
				R.drawable.horse3, R.drawable.horse4 };
		selectedHorseId = new int[] { R.drawable.sel_horse1,
				R.drawable.sel_horse2, R.drawable.sel_horse3,
				R.drawable.sel_horse4 };

		for (int i = 0; i < 4; i++) {
			btnTeam1Horse[i] = (Button) findViewById(btnTeam1Id[i]);
			btnTeam2Horse[i] = (Button) findViewById(btnTeam2Id[i]);

			btnTeam1Horse[i].setOnClickListener(this);
			btnTeam2Horse[i].setOnClickListener(this);
		}
	}

	void selectTeam1Horse(int horseIndex) {
		btnTeam1Horse[tempTeam1Index]
				.setBackgroundResource(horseId[tempTeam1Index]);
		btnTeam1Horse[horseIndex]
				.setBackgroundResource(selectedHorseId[horseIndex]);
		Log.i("selectTeam1Horse", "tempTeam1Index : " + tempTeam1Index
				+ " / horseIndex : " + horseIndex);
		tempTeam1Index = horseIndex;
	}

	void selectTeam2Horse(int horseIndex) {
		btnTeam2Horse[tempTeam2Index]
				.setBackgroundResource(horseId[tempTeam2Index]);
		btnTeam2Horse[horseIndex]
				.setBackgroundResource(selectedHorseId[horseIndex]);
		Log.i("selectTeam2Horse", "tempTeam2Index : " + tempTeam2Index
				+ " / horseIndex : " + horseIndex);
		tempTeam2Index = horseIndex;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.BTN_VS_NAME_CONFIRM:
			team1Name = etTeam1Name.getText().toString();
			team2Name = etTeam2Name.getText().toString();
			if(team1Name.equals("") || team2Name.equals("")) {
				Toast.makeText(VsAddTeamActivity.this, "공백을 채워주세요", 1000).show();
			} else {
				Intent mIntent = new Intent(VsAddTeamActivity.this,
						VsGameActivity.class);
				mIntent.putExtra("team1HorseIndex", tempTeam1Index);
				mIntent.putExtra("team2HorseIndex", tempTeam2Index);
				mIntent.putExtra("team1Name", team1Name);
				mIntent.putExtra("team2Name", team2Name);
				startActivity(mIntent);
				finish();
				
			}
			break;
		case R.id.BTN_VS_1_HORSE1:
			selectTeam1Horse(0);
			break;
		case R.id.BTN_VS_1_HORSE2:
			selectTeam1Horse(1);
			break;
		case R.id.BTN_VS_1_HORSE3:
			selectTeam1Horse(2);
			break;
		case R.id.BTN_VS_1_HORSE4:
			selectTeam1Horse(3);
			break;

		case R.id.BTN_VS_2_HORSE1:
			selectTeam2Horse(0);
			break;
		case R.id.BTN_VS_2_HORSE2:
			selectTeam2Horse(1);
			break;
		case R.id.BTN_VS_2_HORSE3:
			selectTeam2Horse(2);
			break;
		case R.id.BTN_VS_2_HORSE4:
			selectTeam2Horse(3);
			break;
		}
	}
}
