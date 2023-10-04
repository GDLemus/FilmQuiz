package com.example.filmquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;

    ArrayList <Users> list = new ArrayList<>();

    public RvAdapter(Context ctx){
        this.context=ctx;
    }

    public void SetItems(ArrayList<Users>users){
     list.addAll(users);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.onlineitem,parent,false);
        return new UsersVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UsersVH vh = (UsersVH) holder;
        Users users = list.get(position);
        vh.Pprofilename.setText(users.getName());
       // vh.Pprofileimg.setImageURI(users.profile);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

