package edu.sungshin.bookstorming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity2 extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private EditText mEtEmail,mEtpwd;
    String nickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        mFirebaseAuth=FirebaseAuth.getInstance();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("bookstorming"); //database의 경로이다

        mEtEmail=findViewById(R.id.et_email);
        mEtpwd=findViewById(R.id.et_pwd);


        Button btn_login2=findViewById(R.id.btn_login2);
        btn_login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strEmail=mEtEmail.getText().toString();
                String strPwd=mEtpwd.getText().toString();

                mFirebaseAuth.signInWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(LoginActivity2.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            ((Apptest) getApplication() ).setId(nickname);
                            Toast.makeText(LoginActivity2.this, "로그인에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity2.this,TabHostActivity.class);
                            intent.putExtra("userID",strEmail);
                            //((Apptest) getApplication() ).setId(strEmail);

                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(LoginActivity2.this, "로그인 실패..!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });



            }
        });

        Button btn_register2=findViewById(R.id.btn_register2);
        btn_register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity2.this,RegisterActivity2.class);
                startActivity(intent);

            }
        });
    }
}