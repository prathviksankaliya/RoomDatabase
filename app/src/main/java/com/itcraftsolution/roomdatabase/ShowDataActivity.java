package com.itcraftsolution.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.os.Bundle;

import com.itcraftsolution.roomdatabase.databinding.ActivityShowDataBinding;
import com.itcraftsolution.roomdatabase.databinding.RvShowdataSampleBinding;

import java.util.List;

public class ShowDataActivity extends AppCompatActivity {

    private ActivityShowDataBinding binding;
    private RvShowDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppDatabase database = Room.databaseBuilder(ShowDataActivity.this, AppDatabase.class, "room_db").allowMainThreadQueries().build();
        UserDao userDao = database.userDao();
        List<User> users = userDao.getAllRecords();

        adapter = new RvShowDataAdapter(ShowDataActivity.this, users);
        binding.rvShowData.setLayoutManager(new LinearLayoutManager(ShowDataActivity.this));
        binding.rvShowData.setAdapter(adapter);

    }
}