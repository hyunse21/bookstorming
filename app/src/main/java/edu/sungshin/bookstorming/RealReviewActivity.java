package edu.sungshin.bookstorming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RealReviewActivity extends AppCompatActivity {

    private Intent intent;
    String tv_tt,tv_dt;
    TextView book_title,date;
    EditText et1_review;
    Button ok_btn,cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_review);

        book_title=(TextView)findViewById(R.id.tv_tt);
        date=(TextView)findViewById(R.id.tv_dt);


        intent=getIntent();
        tv_tt=intent.getStringExtra("tv_tt");
        tv_dt=intent.getStringExtra("tv_dt");

        book_title.setText(tv_tt);
        date.setText(tv_dt);

        ok_btn=(Button) findViewById(R.id.ok_btn);
        cancel_btn=(Button)findViewById(R.id.cancel_btn);
        et1_review=(EditText) findViewById(R.id.et_review);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String review=et1_review.getText().toString();


                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success  = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),"리뷰등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(RealReviewActivity.this,TabHostActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"리뷰등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                RegisterReviewRequest registerreviewRequest=new RegisterReviewRequest(review,responseListener);
                RequestQueue queue= Volley.newRequestQueue(RealReviewActivity.this);
                queue.add(registerreviewRequest);


            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RealReviewActivity.this,TabHostActivity.class);
                startActivity(intent);
            }
        });

    }
}
