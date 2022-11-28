package edu.sungshin.bookstorming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends AppCompatActivity {

    TextView tv_tt,tv_dp,tv_dt,tv_at;
    String title,description,date,author;
    Button btn_reveiw;
    TextView btn_cart;
    int count=0;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setIcon(R.drawable.logosize);
        getSupportActionBar().setDisplayUseLogoEnabled(true) ;
        getSupportActionBar().setDisplayShowHomeEnabled(true) ;
        Drawable drawable = getResources().getDrawable(R.drawable.title_gradient);
        getSupportActionBar().setBackgroundDrawable(drawable);
        getSupportActionBar().show();

        tv_tt=findViewById(R.id.tv_tt);
        tv_dp=findViewById(R.id.tv_dp);
        tv_dt=findViewById(R.id.tv_dt);
        tv_at=findViewById(R.id.tv_at);
        btn_cart=findViewById(R.id.cart);

        btn_reveiw=findViewById(R.id.btn_review);

        Intent intent=getIntent();
        title=intent.getStringExtra("tv_tt");
        description=intent.getStringExtra("tv_dp");
        date=intent.getStringExtra("tv_dt");
        author=intent.getStringExtra("tv_at");

        tv_tt.setText(title);
        tv_dp.setText(description);
        tv_dt.setText(date);
        tv_at.setText(author);

        btn_reveiw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this,StartActivity_review.class);
                intent.putExtra("tv_tt",title);
                startActivity(intent);
            }
        });

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String userID=((Apptest)getApplication()).getId();

                //if(count%2==0) {
                    Toast.makeText(getApplicationContext(),"찜하셨습니다",Toast.LENGTH_SHORT).show();
                    //CartDTO cart = new CartDTO(title); //ChatDTO를 이용하여 데이터를 묶는다.
                    databaseReference.child(userID).child(title).push().setValue(title); // 데이터 푸쉬
                    //count++;
                //}
                //else{
                    //Toast.makeText(getApplicationContext(),"취소하셨습니다",Toast.LENGTH_SHORT).show();
                    //count++;
                    //databaseReference.child(userID).child(title).setValue(null);

                //}

            }
        });


    }
}