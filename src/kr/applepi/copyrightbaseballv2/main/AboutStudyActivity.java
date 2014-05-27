package kr.applepi.copyrightbaseballv2.main;

import kr.applepi.copyrightbaseballv2.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AboutStudyActivity extends Activity implements OnItemClickListener {
	String[] items = { "저작인격권", "저작재산권", "저작인접권", "저작물", "저작권", "공표권", "성명표시권",
			"동일성유지권", "복제권", "공연권", "공중송신권", "전시권", "배포권", "2차적저작물 작성권", "대여권",
			"어문저작물", "도형저작물", "컴퓨터프로그램 저작물", "편집저작물", "공동저작물", "연극저작물",
			"2차적저작물", "미술저작물", "영상저작물", "저작물 보호기간", "헌법", "시각장애인을 위한 복재",
			"프로그램 역코드 분석", "사적 이용을 위한 복제", "캡쳐", "배경 음악", "저작권관리사", "악보 없는 노래",
			"정치적 연설", "도서관에서의 복제", "번역", "데이터베이스 제작자의 권리", "매장에서의 음악재생",
			"Wii설치", "폰트", "정품 CD 2번이용" };
	String[] captions = {
			"정신적인 노력의 산물로 만들어 낸 저작물에 대해 저작자가 인격적으로 갖는 권리로 양도가 불가능해요.",
			"저작자가 자신이 만든 저작물을 다양한 방식으로 이용함으로써 재산적 이익을 얻을 수 있는 권리로 양도가 가능해요.",
			"저작물을 사람들에게 전달하는 데에 특별한 역할을 한 사람에게 부여되는 권리로 실제연주자등이 포함되요.",
			"사람의 사상이나 감정을 표현한 창작 결과물이에요.",
			"저작권은 저작물의 창작이 있기만 하면 자연히 발생해요.",
			"저작물을 공표할 것인지 말 것인지, 언제 어떤 방법으로 공표할 것인지를 정할 권리로 저작인접권에 포함되요.",
			"저작물을 공표할 때 어떤 이름으로 표시할 지, 표시하지 않을 권리로 저작인접권에 포함되요.",
			"저작물을 원형 그대로 보존, 허락 없이 다른 사람에 의해 변경, 삭제 되지 않을 권리로 저작인접권에 포함되요.",
			"저작물을 인쇄, 복사, 사진을 찍거나 음악 CD의 곡을 MP3로 변환하는 권리로 저작재산권에 포함되요.",
			"여러 사람들 앞에서 저작물을 연주하거나 상영하거나 가창, 녹음기 재생 등을 하여 공개할 수 있는 권리로 저작재산권에 포함되요.",
			"여러 사람들이 저작물을 수신하거나 접근할 수 있도록 송신하거나 제공하는 권리로 저작재산권에 포함되요.",
			"미술 작품, 사진, 건축물 같은 저작물의 원본이나 복제물을 전시할 권리로 저작재산권에 포함되요.",
			"저작물의 원본이나 복제물을 여러 사람들에게 나눠 주거나 빌려 줄 수 있는 권리로 저작재산권에 포함되요.",
			"원래 있던 저작물을 번역, 편곡, 변형, 각색하거나 영상으로 제작하는 등의 방법으로 이용할 권리로 저작재산권에 포함되요.",
			"판매용음반과 판매용컴퓨터프로그램에 대해서만 인정되는 권리로, 한번 판매되었더라도 저작권자의 허락을 얻지 않으면 영리의 목적으로 쓸 수 없는 권리로 저작재산권에 포함되요.",
			"말과 글로 표현된 저작물이에요.",
			"지도, 도표, 설계도, 약도, 모형, 그 밖의 도형으로 표현된 저작물이에요.",
			"특정한 결과를 얻기위해서 컴퓨터 내에서 직접 또는 간접으로 사용되는 일련의 지시나 명령을 표현되는 저작물이에요.",
			"원래 있던 저작물이나 부호, 문자, 음성, 음향, 영상, 그 밖의 자료 등 소재들을 묶어 놓은 것 중 소재의 선택이나 배열 또는 구성에 창작성이 있는 저작물이에요.",
			"2인 이상의 여러 명이 창작한 저작물로서 그 여러 명 각자가 기여한 부분을 분리하여 이용할 수 없는 저작물로 보호기간은 나중에 사망한 저작자를 기준으로 해요.",
			"연극이나 무용, 뮤지컬 등으로 표현된 저작물이에요.",
			"원서를 번역한 책, 90년대 히트곡을 편곡한 음악등으로 표현된 저작물이에요.",
			"선과 모양, 색채로 표현된 저작물이에요.", "비디오 게임의 영상, 영화나 광고가 속하는 저작물이에요.",
			"공동저작물은 나중에 사망한 저작자를 기준으로하며 그 외 저작물은 저작자가 죽은 다음해 부터 70년동안 보호되요.",
			"헌법은 보호받을 수 없는 저작물이에요.",
			"시각장애인 등을 위한 복재는 저작권자의 허락을 받지 않고도 저작물을 이용할 수 있어요.",
			"프로그램 역코드 분석은 저작권자의 허락을 받지 않고도 가능해요.",
			"사적 이용을 위한 복제는 저작권자의 허락을 받지 않아도 가능해요.",
			"인기 드라마 ,쇼 프로 방송 프로그램을 캡쳐하여 인터넷에 올리는 것은 저작권을 침해한 행동이에요.",
			"음악을 내 홈피나 블로그에 배경 음악으로 쓰는 것은 저작권을 침해한 행동이에요.",
			"창작자와 이용자간의 분쟁을 조정하고 창작물의 권리를 보호하며, 위탁관리와 중개업무를 대신 해주는 전문가에요.",
			"악보 없이 직접 연주하거나 부른 노래도 음악저작물로 보호되요.",
			"정치적 연설등에 저작물이 사용될 경우 저작권자의 허락을 받지 않고도 저작물을 이용할 수 있어요.",
			"도서관 등에서의 복제는 저작권자에게 허락을 받지 않아도 가능해요.",
			"번역 등에 의한 이용은 저작권자에게 허락을 받지 않아도 가능해요.",
			"데이터베이스 제작자의 권리는 제작을 완료한 때 발생하여 그 다음해부터 5년동안 보호되요.",
			"매장에서 음악CD를 재생한 것은 저작권을 침해한 행동이라고 판결났어요.",
			"모텔에 Wii를 설치하는 것은 저작권을 침해한 행동이라고 판결났어요.", "폰토에도 저작권이 있어요.",
			"정품 포토샵 CD 1장을 구입하여 두 대의 컴퓨터에 설치한 경우는 저작권을 침해한 행동이라고 판결났어요." };

	ListView listView;
	ArrayAdapter<String> adapter;
	AlertDialog.Builder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about_study);
		initUI();
		allocListView();
	}

	void initUI() {
		listView = (ListView) findViewById(R.id.LV_ABOUT_STUDY);
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
		builder.setTitle(items[position]);
		builder.setMessage(captions[position]);
		builder.setNeutralButton("알겠어요!", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		builder.create().show();
	}

}
