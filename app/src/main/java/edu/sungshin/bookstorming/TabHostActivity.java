package edu.sungshin.bookstorming;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


@SuppressWarnings("deprecation")
public class TabHostActivity extends TabActivity {


    private BroadcastReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host);
        mReceiver = new broadCastRece();



        TabHost tabHost = getTabHost(); //탭 호스트 객체 생성

// 탭스팩 선언하고, 탭의 내부 명칭, 탭에 출력될 글 작성
        TabHost.TabSpec spec;
        Intent intent; //객체

        //커스텀
        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        TabHost finalTabHost = tabHost;
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for(int i = 0; i< finalTabHost.getTabWidget().getChildCount(); i++)
                {
                    finalTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.white);
                }

                finalTabHost.getTabWidget().getChildAt(finalTabHost.getCurrentTab()).setBackgroundResource(R.drawable.brown);
                }
        });



//탭에서 액티비티를 사용할 수 있도록 인텐트 생성
        intent = new Intent().setClass(this, MainActivity.class);
        spec = tabHost.newTabSpec("Study"); // 객체를 생성
        spec.setIndicator("책 검색"); //탭의 이름 설정
        spec.setContent(intent);
        tabHost.addTab(spec);



//탭에서 액티비티를 사용할 수 있도록 인텐트 생성
        intent = new Intent().setClass(this, StartActivity.class);
        spec = tabHost.newTabSpec("Time"); // 객체를 생성
        spec.setIndicator("중고서점"); //탭의 이름 설정
        spec.setContent(intent);
        tabHost.addTab(spec);




//탭에서 액티비티를 사용할 수 있도록 인텐트 생성
        intent = new Intent().setClass(this, GalleryActivity.class);
        spec = tabHost.newTabSpec("Together_home"); // 객체를 생성
        spec.setIndicator("나의 책방"); //탭의 이름 설정
        spec.setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0); //먼저 열릴 탭을 선택! (2)로 해두면 그룹이 시작 화면!
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // 필터를 정의하여 broadCastRece클래스에 전송
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(mReceiver);
    }


}
