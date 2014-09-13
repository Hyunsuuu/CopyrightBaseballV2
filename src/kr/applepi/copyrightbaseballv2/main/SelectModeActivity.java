package kr.applepi.copyrightbaseballv2.main;

import kr.applepi.copyrightbaseballv2.R;
import kr.applepi.copyrightbaseballv2.single.SingleAddTeamNameActivity;
import kr.applepi.copyrightbaseballv2.vs.VsAddTeamActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SelectModeActivity extends Activity implements OnClickListener {

	Button btnSingleMode;
	Button btnVsMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_select_mode);

		initUI();
		allocBtn();
	}

	void initUI() {
		btnSingleMode = (Button) findViewById(R.id.BTN_SINGLE_MODE);
		btnVsMode = (Button) findViewById(R.id.BTN_VS_MODE);
	}

	void allocBtn() {
		Button[] arrBtn = { btnSingleMode, btnVsMode };
		for (Button btn : arrBtn) {
			btn.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.BTN_SINGLE_MODE:
			startActivity(new Intent(SelectModeActivity.this,
					SingleAddTeamNameActivity.class));
			break;
		case R.id.BTN_VS_MODE:
			startActivity(new Intent(SelectModeActivity.this,
					VsAddTeamActivity.class));
			break;
		}
	}
}
