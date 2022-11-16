package edu.sungshin.bookstorming;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {


    private ArrayList<item> arrayList;
    private Context context;


    public BookAdapter(ArrayList<item> arrayList, Context context) {
        super();
        this.arrayList = arrayList;
        this.context=context;
    }



    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        BookViewHolder holder =new BookViewHolder(view);

        return holder;
    }

    @Override//imageview불러오기
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {

        Glide.with(holder.itemView).load(arrayList.get(position).getProfile()).into(holder.tv_profile);
        holder.tv_tt.setText(arrayList.get(position).getTitle());
        holder.tv_dp.setText(arrayList.get(position).getDescription());
        holder.tv_dt.setText(String.valueOf(arrayList.get(position).getPubDate()));
        holder.btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tv_tt=holder.tv_tt.getText().toString();
                String tv_dp=holder.tv_dp.getText().toString();
                String tv_dt=holder.tv_dt.getText().toString();
                Intent intent;
                intent = new Intent(context,StartActivity_review.class);
                intent.putExtra("tv_tt",tv_tt);
                intent.putExtra("tv_dp",tv_dp);
                intent.putExtra("tv_dt",tv_dt);
                ((MainActivity)context).startActivity(intent);
            }
        });

        holder.tv_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tv_tt=holder.tv_tt.getText().toString();
                String tv_dp=holder.tv_dp.getText().toString();
                String tv_dt=holder.tv_dt.getText().toString();
                Intent intent;
                intent = new Intent(context,DetailActivity.class);
                intent.putExtra("tv_tt",tv_tt);
                intent.putExtra("tv_dp",tv_dp);
                intent.putExtra("tv_dt",tv_dt);
                ((MainActivity)context).startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {//사망연산자
        return (arrayList !=null?arrayList.size():0);
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
        ImageView tv_profile;
        TextView tv_tt;
        TextView tv_dp;
        TextView tv_dt;
        Button btn_review;

        public BookViewHolder(@NonNull View itemView){
            super(itemView);
            this.tv_profile=itemView.findViewById(R.id.tv_profile);
            this.tv_tt=itemView.findViewById(R.id.tv_tt);
            this.tv_dp=itemView.findViewById(R.id.tv_dp);
            this.tv_dt=itemView.findViewById(R.id.tv_dt);
            this.btn_review=itemView.findViewById(R.id.btn_review);

        }

    }
}