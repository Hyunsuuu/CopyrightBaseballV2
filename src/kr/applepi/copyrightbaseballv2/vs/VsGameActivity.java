package kr.applepi.copyrightbaseballv2.vs;

import kr.applepi.copyrightbaseballv2.R;
import kr.applepi.copyrightbaseballv2.R.layout;
import kr.applepi.copyrightbaseballv2.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class VsGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vs_game);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vs_game, menu);
		return true;
	}

}
