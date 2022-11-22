package edu.sungshin.bookstorming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
    private BookAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<item> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    Button btn_review;

    private static final String TAG = "MainActivity";
    String themeColor;

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
                themeColor = ThemeUtil.LIGHT_MODE;
                //themeColor = ThemeUtil.modLoad(getApplicationContext());
                ThemeUtil.applyTheme(themeColor);
                ThemeUtil.modSave(getApplicationContext(), themeColor);
                return true;

            case R.id.menu2:
                Toast.makeText(this, "다크모드 선택", Toast.LENGTH_SHORT).show();
                themeColor = ThemeUtil.DARK_MODE;
                //themeColor = ThemeUtil.modLoad(getApplicationContext());
                ThemeUtil.applyTheme(themeColor);
                ThemeUtil.modSave(getApplicationContext(), themeColor);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<>();// 책 객체를 담을 어레이 리스트
        adapter = new BookAdapter();

        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("item");

        btn_review=findViewById(R.id.btn_review);
        recyclerView=findViewById(R.id.recyclerVies);
        recyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(this);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);//리사이클러 뷰에 어댑터 연결

        searchAnimal();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 데이터베이스에 데이터를 받아오는 메소드
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    item item = snapshot.getValue(item.class);//북 객체에 데이터 담음
                    arrayList.add(item);//담은 데이터를 배열리스트에 추가
                }
                adapter.setItemList(arrayList);
                adapter.notifyDataSetChanged();//리스트 저장및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity",String.valueOf(databaseError.toException()));
            }
        });
    }



    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(MainActivity.this);
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
                    adapter.notifyDataSetChanged();//리스트 저장및 새로고침
                }
                adapter.setItemList(filterAnimal);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

    }

}