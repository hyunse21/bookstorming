package edu.sungshin.bookstorming;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class broadCastRece extends BroadcastReceiver {

    public final static String MyAction = "com.example.broadcasttest.ACTION_MY_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {
        // 전원연결 및 전원해제 시 Toast메시지를 띄운다

        if(Intent.ACTION_POWER_CONNECTED.equals(intent.getAction()))
        {
            Toast.makeText(context, "전원 연결", Toast.LENGTH_SHORT).show();
        }
        else if(Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction()))
        {
            Toast.makeText(context, "전원 연결 해제", Toast.LENGTH_SHORT).show();
        }
        else if(Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction()))
        {
            Toast.makeText(context, "비행기모드 변경! 와이파이 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
        }
    }

}