package kr.applepi.copyrightbaseballv2.vs;

import kr.applepi.copyrightbaseballv2.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class VsGameActivity extends Activity implements OnClickListener{

	TextView tvQuestion;

	Button btnO;
	Button btnX;

	ImageView[] team1Base = new ImageView[4];
	ImageView[] team2Base = new ImageView[4];
	
	int[] team1BaseId;
	int[] team2BaseId;
	int[] horseId;

	Drawable[] horse;
	Drawable team1Horse;
	Drawable team2Horse;

	int team1HorseIndex;
	int team2HorseIndex;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_vs_game);
		
		initUi();
	}

	void initUi() {
		btnO = (Button) findViewById(R.id.BTN_VS_O);
		btnX = (Button) findViewById(R.id.BTN_VS_X);

		team1BaseId = new int[] { R.id.IMG_VS_1_HOME, R.id.IMG_VS_1_FIRST_BASE,
				R.id.IMG_VS_1_SECOND_BASE, R.id.IMG_VS_1_THIRD_BASE };
		team2BaseId = new int[] { R.id.IMG_VS_2_HOME, R.id.IMG_VS_2_FIRST_BASE,
				R.id.IMG_VS_2_SECOND_BASE, R.id.IMG_VS_2_THIRD_BASE };
		horseId = new int[] { R.drawable.horse1, R.drawable.horse2,
				R.drawable.horse3, R.drawable.horse4 };
		for (int i = 0; i < 4; i++) {
			team1Base[i] = (ImageView) findViewById(team1BaseId[i]);
			team2Base[i] = (ImageView) findViewById(team2BaseId[i]);
			horse[i] = getResources().getDrawable(horseId[i]);
		}

		Intent mIntent = getIntent();
		team1HorseIndex = mIntent.getIntExtra("team1HorseIndex", 0);
		team2HorseIndex = mIntent.getIntExtra("team2HorseIndex", 0);
		
		team1Horse = horse[team1HorseIndex];
		team2Horse = horse[team2HorseIndex];
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.BTN_VS_O :
			break;
		case R.id.BTN_VS_X :
			break;
		}
	}
}
