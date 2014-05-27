package kr.applepi.copyrightbaseballv2.main;

import kr.applepi.copyrightbaseballv2.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	Button btnStartGame;
	Button btnAboutSites;
	Button btnStudy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initUI();
		allocBtn();
	}

	void initUI() {
		btnStartGame = (Button) findViewById(R.id.BTN_START_GAME);
		btnAboutSites = (Button) findViewById(R.id.BTN_ABOUT_SITES);
		btnStudy = (Button) findViewById(R.id.BTN_STUDY);
	}

	void allocBtn() {
		Button[] arrBtn = { btnStartGame, btnAboutSites, btnStudy };
		for (Button btn : arrBtn) {
			btn.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.BTN_START_GAME:
			startActivity(new Intent(MainActivity.this, SelectModeActivity.class));
			break;
		case R.id.BTN_ABOUT_SITES:
			startActivity(new Intent(MainActivity.this, AboutSitesActivity.class));
			break;
		case R.id.BTN_STUDY:
			startActivity(new Intent(MainActivity.this, AboutStudyActivity.class));
			break;
		}
	}
}
