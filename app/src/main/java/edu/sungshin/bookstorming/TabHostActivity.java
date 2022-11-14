package edu.sungshin.bookstorming;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;


@SuppressWarnings("deprecation")
public class TabHostActivity extends TabActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host);



        TabHost tabHost = getTabHost(); //탭 호스트 객체 생성

// 탭스팩 선언하고, 탭의 내부 명칭, 탭에 출력될 글 작성
        TabHost.TabSpec spec;
        Intent intent; //객체




//탭에서 액티비티를 사용할 수 있도록 인텐트 생성
        intent = new Intent().setClass(this, MainActivity.class);
        spec = tabHost.newTabSpec("Study"); // 객체를 생성
        spec.setIndicator("책리스트"); //탭의 이름 설정
        spec.setContent(intent);
        tabHost.addTab(spec);



//탭에서 액티비티를 사용할 수 있도록 인텐트 생성
        intent = new Intent().setClass(this, StartActivity.class);
        spec = tabHost.newTabSpec("Time"); // 객체를 생성
        spec.setIndicator("채팅"); //탭의 이름 설정
        spec.setContent(intent);
        tabHost.addTab(spec);




//탭에서 액티비티를 사용할 수 있도록 인텐트 생성
        intent = new Intent().setClass(this, MainActivity.class);
        spec = tabHost.newTabSpec("Together_home"); // 객체를 생성
        spec.setIndicator("나의책방"); //탭의 이름 설정
        spec.setContent(intent);
        tabHost.addTab(spec);



        tabHost.setCurrentTab(0); //먼저 열릴 탭을 선택! (2)로 해두면 그룹이 시작 화면!


    }


}
