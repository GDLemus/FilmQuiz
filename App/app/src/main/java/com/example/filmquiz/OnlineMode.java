package com.example.filmquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OnlineMode extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;

    RvAdapter adapter;
    RecyclerView recyclerView;

    DAOUsuario dao;

    boolean isLoading;
    String key = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_mode);

        swipeRefreshLayout= findViewById(R.id.swip);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new RvAdapter(this);
        recyclerView.setAdapter(adapter);
        dao = new DAOUsuario();
        loadData();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int totalItems = linearLayoutManager.getItemCount();
                    int lastvisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    if(!isLoading){
                        isLoading=true;
                        loadData();
                    }

                }

            });
        }

    private void loadData(){
        dao.get(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Users> users = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()){
                    Users user = data.getValue(Users.class);
                    users.add(user);
                    key=data.getKey();
                }
            adapter.SetItems(users);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
