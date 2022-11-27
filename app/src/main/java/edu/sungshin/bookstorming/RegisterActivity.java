package edu.sungshin.bookstorming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {


    private EditText et_id,et_pass,et_name,et_age,et_passcheck;
    private Button btn_register,btn_check,btn_check_pass;
    private boolean validate=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        et_id=findViewById(R.id.et_id);
        et_pass=findViewById(R.id.et_pass);
        et_name=findViewById(R.id.et_name);
        et_age=findViewById(R.id.et_age);
        btn_register=findViewById(R.id.btn_register);
        btn_check=findViewById(R.id.checkid);
        et_passcheck=findViewById(R.id.et_passcheck);


            btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID=et_id.getText().toString().trim();
                String userPass=et_pass.getText().toString().trim();
                String userName=et_name.getText().toString();
                String checkage= et_age.getText().toString();
                String userPasscheck=et_passcheck.getText().toString().trim();



                if(checkage.equals("")){
                    Toast.makeText(getApplicationContext(), "모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int userAge=Integer.parseInt(et_age.getText().toString());

                if((userID.equals(""))||(userPass.equals(""))||(userName.equals(""))) {
                    Toast.makeText(getApplicationContext(), "모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(btn_check.getText().equals("중복확인")){
                    Toast.makeText(getApplicationContext(), "아이디 중복을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(!userPass.contains("!")&&!userPass.contains("@")&&!userPass.contains("^")&&!userPass.contains("&")&&!
                        userPass.contains("*")&&!userPass.contains("(")&&!userPass.contains(")")){
                    Toast.makeText(getApplicationContext(), "비밀번호 포함 문자를 확인해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!userPass.contains("0")&&!userPass.contains("1")&&!userPass.contains("2")&&!userPass.contains("3")&&!
                        userPass.contains("4")&&!userPass.contains("5")&&!userPass.contains("6")&&!userPass.contains("7")
                        &&!userPass.contains("8")&&!userPass.contains("9")){
                    Toast.makeText(getApplicationContext(), "비밀번호 포함 문자를 확인해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!userPass.contains("A")&&!userPass.contains("B")&&!userPass.contains("C")&&!userPass.contains("D")&&!
                        userPass.contains("E")&&!userPass.contains("F")&&!userPass.contains("G")&&!userPass.contains("H")
                        &&!userPass.contains("I")&&!userPass.contains("J")&&!userPass.contains("K")&&!userPass.contains("L")
                        &&!userPass.contains("M")&&!userPass.contains("N")&&!userPass.contains("O")
                        &&!userPass.contains("P")&&!userPass.contains("Q")&&!userPass.contains("R")&&!userPass.contains("S")
                        &&!userPass.contains("T")&&!userPass.contains("U")&&!userPass.contains("V")&&!userPass.contains("W")
                        &&!userPass.contains("X")&&!userPass.contains("Y")&&!userPass.contains("Z")){
                    Toast.makeText(getApplicationContext(), "비밀번호 포함 문자를 확인해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!userPass.contains("a")&&!userPass.contains("b")&&!userPass.contains("c")&&!userPass.contains("d")&&!
                        userPass.contains("e")&&!userPass.contains("f")&&!userPass.contains("g")&&!userPass.contains("h")
                        &&!userPass.contains("i")&&!userPass.contains("j")&&!userPass.contains("k")&&!userPass.contains("l")
                        &&!userPass.contains("m")&&!userPass.contains("n")&&!userPass.contains("o")
                        &&!userPass.contains("p")&&!userPass.contains("q")&&!userPass.contains("r")&&!userPass.contains("s")
                        &&!userPass.contains("t")&&!userPass.contains("u")&&!userPass.contains("v")&&!userPass.contains("w")
                        &&!userPass.contains("x")&&!userPass.contains("y")&&!userPass.contains("z")){
                    Toast.makeText(getApplicationContext(), "비밀번호 포함 문자를 확인해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(userPass.length()<6){
                    Toast.makeText(getApplicationContext(), "6자리 이상의 비밀번호를 설정하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!userPass.equals(userPasscheck)){
                    Toast.makeText(getApplicationContext(), "비밀번호가 같지 않습니다", Toast.LENGTH_SHORT).show();
                    return;
                }



                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success  = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),"회원등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else{

                                    Toast.makeText(getApplicationContext(), "회원등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                RegisterRequest registerRequest=new RegisterRequest(userID,userPass,userName,userAge,responseListener);
                RequestQueue queue= Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });



        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID=et_id.getText().toString().trim();
                if(validate)
                {
                    return;
                }
                if(userID.equals("")){
                    Toast.makeText(getApplicationContext(),"아이디는 빈 칸일 수 없습니다",Toast.LENGTH_SHORT).show();
                    return;
                }
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),"사용할 수 있는 아이디입니다.",Toast.LENGTH_SHORT).show();
                                et_id.setEnabled(false);
                                validate=true;
                                btn_check.setText("확인");
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"사용할 수 없는 아이디입니다.",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                ValidateRequest validateRequest=new ValidateRequest(userID,responseListener);
                RequestQueue queue= Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);

            }
        });

    }
    void check_validation(String email, String password){
        // 비밀번호 유효성 검사식1 : 숫자, 특수문자가 포함되어야 한다.
        String val_symbol = "([0-9].*[!,@,#,^,&,*,(,)])|([!,@,#,^,&,*,(,)].*[0-9])";
        // 비밀번호 유효성 검사식2 : 영문자 대소문자가 적어도 하나씩은 포함되어야 한다.
        String val_alpha = "([a-z].*[A-Z])|([A-Z].*[a-z])";
        // 정규표현식 컴파일
        Pattern pattern_symbol = Pattern.compile(val_symbol);
        Pattern pattern_alpha = Pattern.compile(val_alpha);

        Matcher matcher_symbol = pattern_symbol.matcher(password);
        Matcher matcher_alpha = pattern_alpha.matcher(password);

        if (matcher_symbol.find() && matcher_alpha.find()) {
            // email과 password로 회원가입 진행
            //email_signIn(email, password);
        } else {
            Toast.makeText(this, "비밀번호로 부적절합니다", Toast.LENGTH_SHORT).show();
        }
    }
}