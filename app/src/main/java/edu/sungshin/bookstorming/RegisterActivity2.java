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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity2 extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private EditText mEtEmail,mEtpwd,mEtnickname;
    private Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);


        mFirebaseAuth=FirebaseAuth.getInstance();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("bookstorming"); //database의 경로이다

        mEtEmail=findViewById(R.id.et_email);
        mEtpwd=findViewById(R.id.et_pwd);
        mBtnRegister=findViewById(R.id.btn_register2);
        //mEtnickname=findViewById(R.id.et_nickname);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail=mEtEmail.getText().toString();
                String strPwd=mEtpwd.getText().toString();
               // String strNickname=mEtnickname.getText().toString();


                mFirebaseAuth.createUserWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(RegisterActivity2.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser=mFirebaseAuth.getCurrentUser();
                            UserAccount account=new UserAccount();
                            account.setIdToken(firebaseUser.getUid());//로그인후 얻는 고유값
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);
                            //account.setNickname(strNickname);

                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);//database에 삽입하는 구문

                            Toast.makeText(RegisterActivity2.this,"회원가입에 성공하셨습니다",Toast.LENGTH_SHORT).show();
                            //((Apptest) getApplication() ).setId(strEmail);
                            Intent intent=new Intent(RegisterActivity2.this,LoginActivity2.class);
                            //intent.putExtra("nickname",strNickname);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(RegisterActivity2.this,"회원가입에 실패하셨습니다",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}