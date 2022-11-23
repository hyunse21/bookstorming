package edu.sungshin.bookstorming;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private Context context;
    private List<User> userList;

    private  final int GET_GALLERY_IMAGE =200;
    ImageView ivProfile;
    TextView userID;
    String getUserID;
    private ListView chat_list;
    private Intent intent;
    Button btn_logout,btn_delete;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Gallery gallery=(Gallery) findViewById(R.id.gallery);
        GalleryActivity.MyGalleryAdapter galAdapter=new GalleryActivity.MyGalleryAdapter(this);
        gallery.setAdapter(galAdapter);


        btn_logout=findViewById(R.id.btn_logout);
        btn_delete=findViewById(R.id.deleteButton);


        Button logoutButton = (Button) findViewById(R.id.btn_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(GalleryActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(GalleryActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);

                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(intent);
            }

        });


        ivProfile = findViewById(R.id.ivPoster);
        userID=findViewById(R.id.userID);
        chat_list = (ListView) findViewById(R.id.chat_list);

        getUserID= ( (Apptest) getApplication() ).getId();
        userID.setText(getUserID);

        ivProfile.setClipToOutline(true);


        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Intent.ACTION_PICK);
                //intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                //startActivityForResult(intent,GET_GALLERY_IMAGE);

            }
        });

        showChatList();

        btn_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //4. 콜백 처리부분(volley 사용을 위한 ResponseListener 구현 부분)
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //받아온 값이 success면 정상적으로 서버로부터 값을 받은 것을 의미함
                            if(success){
                                finish();
                                Toast.makeText(getApplicationContext(),"회원탈퇴 되었습니다!",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(GalleryActivity.this, LoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);
                                userList.remove(userID);//리스트에서 해당부분을 지워줌

                            }
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                DeleteRequest deleteRequest = new DeleteRequest(userID.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(GalleryActivity.this);
                queue.add(deleteRequest);
            }//onclick
        });

    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE&&resultCode==RESULT_OK&&data!=null
                &&data.getData()!=null) {
            Uri selectedImageUri = data.getData();
            ivProfile.setImageURI(selectedImageUri);
            ivProfile.setMaxWidth(100);
            ivProfile.setMaxHeight(100);

        }
    }

    private void showChatList() {
        // 리스트 어댑터 생성 및 세팅
        final ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        adapter.notifyDataSetChanged();
        chat_list.setAdapter(adapter);

        String userID = ((Apptest)getApplication()).getId();

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child(userID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e("LOG", "dataSnapshot.getKey() : " + dataSnapshot.getKey());
                adapter.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        adapter.notifyDataSetChanged();

    }
    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(GalleryActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.activity_dialog);
        dialog.show();
        Button button = (Button)dialog.findViewById(R.id.backbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button button1 = (Button)dialog.findViewById(R.id.exitbtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public class MyGalleryAdapter extends BaseAdapter {
        Context context;
        Integer[] posterID = {R.drawable.img_2, R.drawable.img_3, R.drawable.img_4, R.drawable.img_5};

        public MyGalleryAdapter(Context c) {
            context = c;
        }

        public int getCount() {
            return posterID.length;
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageview = new ImageView(context);
            imageview.setLayoutParams(new Gallery.LayoutParams(200, 300));
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageview.setPadding(5, 5, 5, 5);
            imageview.setImageResource(posterID[position]);

            final int pos = position;
            imageview.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    ImageView ivPoster = (ImageView) findViewById(R.id.ivPoster);
                    ivPoster.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    ivPoster.setImageResource(posterID[pos]);
                    return false;
                }
            });

            return imageview;
        }
    }
}


