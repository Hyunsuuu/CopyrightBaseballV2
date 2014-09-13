package kr.applepi.copyrightbaseballv2.main;

import kr.applepi.copyrightbaseballv2.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AboutSitesActivity extends Activity implements OnItemClickListener {

	String[] items = { "어린이청소년 저작권교실", "한국저작권위원회", "저작권 등록시스템", "한국음악저작권협회",
			"저작권보호센터", "한국저작권중앙회", "저작권교육 포털서비스", "굿 다운로더",
			"한국소프트웨어저작권사용자보호협회" };
	String[] captions = { "한국저작권위원회 운영, 저작권 관련 정보, 교육프로그램, 애니메이션, 게임 등을 안내해요.",
			"저작권 상담, 등록, 조정, 용어, 법령 및 조약, 판례 정보 등을 제공해요.",
			"저작권 업무 처리 ,등록안내, 온라인 등록신청, 저작권 정보 검색서비스 등을 제공해요.",
			"협회 소개, 민원, 침해사례제보, 작품등록 및 검색, 이용계약신청 등을 안내해요.",
			"저작권 침해신고 및 법률, 침해정보, 보호기술, 인터넷상담 등을 안내해요.",
			"저작권관리사 자격증  취득 교육기관, 교육과정, 자격시험 등을 안내해요.",
			"저작권 아카데미, 청소년, 학부모 등 교육과정 등을 안내해요.",
			"합법 다운로드 권장 캠페인, CF영상, 월페이퍼, 저작권 상식, 보도자료 등을 제공해요.",
			"SW사용자 권익보호, 소프트웨어 자산관리, 정품소프트웨어, SW저작권, 클라우드컴퓨팅 등을 안내해요." };
	String[] sites = { "http://youth.copyright.or.kr/",
			"http://www.copyright.or.kr/main/index.do",
			"http://www.cros.or.kr/main.cc", "http://www.komca.or.kr",
			"http://www.cleancopyright.or.kr/", "http://www.crmanager.co.kr/",
			"http://portal.edu-copyright.or.kr/",
			"http://www.gooddownloader.com/",
			"http://www.kosupa.or.kr/m/index.html" };

	ListView listView;
	ArrayAdapter<String> adapter;
	AlertDialog.Builder builder;

	static int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about_sites);

		initUI();
		allocListView();
	}

	void initUI() {
		listView = (ListView) findViewById(R.id.LV_ABOUT_SITES);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		builder = new AlertDialog.Builder(this);
		listView.setAdapter(adapter);
	}

	void allocListView() {
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		this.position = position;
		builder.setTitle(items[position]);
		builder.setMessage(captions[position]);
		builder.setPositiveButton("알겠어요!", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.setNeutralButton("가볼래요!", new OnClickListener() {

			private int position = AboutSitesActivity.position;

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Uri uri = Uri.parse(sites[position]);
				startActivity(new Intent(Intent.ACTION_VIEW, uri));

			}
		});
		builder.create().show();
	}

}
