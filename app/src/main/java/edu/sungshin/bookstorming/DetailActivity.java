package edu.sungshin.bookstorming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView tv_tt,tv_dp,tv_dt;
    String title,description,date;
    Button btn_reveiw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv_tt=findViewById(R.id.tv_tt);
        tv_dp=findViewById(R.id.tv_dp);
        tv_dt=findViewById(R.id.tv_dt);

        btn_reveiw=findViewById(R.id.btn_review);

        Intent intent=getIntent();
        title=intent.getStringExtra("tv_tt");
        description=intent.getStringExtra("tv_dp");
        date=intent.getStringExtra("tv_dt");

        tv_tt.setText(title);
        tv_dp.setText(description);
        tv_dt.setText(date);

        btn_reveiw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this,StartActivity_review.class);
                intent.putExtra("tv_tt",title);
                startActivity(intent);
            }
        });


    }
}