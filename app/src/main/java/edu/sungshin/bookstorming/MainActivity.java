package edu.sungshin.bookstorming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final long finishtimeed = 1000;
    private long presstime = 0;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<item> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    Button btn_review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_review=findViewById(R.id.btn_review);

        searchAnimal();
        setUpList();


        recyclerView=findViewById(R.id.recyclerVies);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();//책 객체를 담을 어레이 리스트

        database=FirebaseDatabase.getInstance();

        databaseReference=database.getReference("item");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스에 데이터를 받아오는 메소드
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    item item = snapshot.getValue(item.class);//북 객체에 데이터 담음
                    arrayList.add(item);//담은 데이터를 배열리스트에 추가
                }
                adapter.notifyDataSetChanged();//리스트 저장및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity",String.valueOf(databaseError.toException()));

            }
        });

        adapter = new BookAdapter(arrayList,this);
        recyclerView.setAdapter(adapter);//리사이클러 뷰에 어댑터 연결


    }
    private void searchAnimal(){

        SearchView searchView=findViewById(R.id.animal_search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<item> filterAnimal=new ArrayList<>();
                for(int i=0;i<arrayList.size();i++){
                    item item=arrayList.get(i);

                    if(item.getTitle().toLowerCase().contains(newText.toLowerCase())){
                        filterAnimal.add(item);
                    }
                }
                BookAdapter adapter=new BookAdapter(filterAnimal,getApplicationContext());
                recyclerView.setAdapter(adapter);
                return false;
            }
        });

    }
    private void setUpList(){
        recyclerView=findViewById(R.id.recyclerVies);
        BookAdapter adapter= new BookAdapter(arrayList,getApplicationContext());
        recyclerView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_option,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                Toast.makeText(this, "라이트모드 선택", Toast.LENGTH_SHORT).show();

                break;

            case R.id.menu2:
                Toast.makeText(this, "다크모드 선택", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);

    }



    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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

                //long tempTime = System.currentTimeMillis();
                //long intervalTime = tempTime - presstime;

                //if (0 <= intervalTime && finishtimeed >= intervalTime)
                //{
                //    finish();
                //}
                //else
                //{
                //   presstime = tempTime;
                //  Toast.makeText(getApplicationContext(), "한번더 누르시면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
}