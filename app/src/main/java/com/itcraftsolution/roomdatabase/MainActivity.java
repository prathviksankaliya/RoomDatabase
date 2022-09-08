package com.itcraftsolution.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.itcraftsolution.roomdatabase.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updateData();

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new bgThread().start();
                if(getIntent().getBooleanExtra("update", false)) {

                    Toast.makeText(MainActivity.this, "Updated!!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Inserted!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShowDataActivity.class));
            }
        });
    }

    class bgThread extends Thread
    {
        @Override
        public void run() {
            super.run();
            AppDatabase database = Room.databaseBuilder(MainActivity.this, AppDatabase.class, "room_db").build();
            UserDao userDao = database.userDao();
            if(getIntent().getBooleanExtra("update", false))
            {
                userDao.updateById(getIntent().getIntExtra("id", 0), binding.edFirstname.getText().toString(), binding.edLastName.getText().toString());
            }else{
                userDao.insertAll(new User(Integer.parseInt(binding.id.getText().toString()) , binding.edFirstname.getText().toString(), binding.edLastName.getText().toString()));
            }
            binding.edFirstname.setText("");
            binding.edLastName.setText("");
            binding.id.setText("");
        }
    }

    private void updateData()
    {
        if(getIntent().getBooleanExtra("update", false))
        {
            binding.btnNext.setText("Update");
            String fName = getIntent().getStringExtra("fName");
            String lName = getIntent().getStringExtra("lName");
            int id = getIntent().getIntExtra("id", 0);

            binding.edFirstname.setText(fName);
            binding.edLastName.setText(lName);
            binding.id.setText(String.valueOf(id));
            binding.id.setEnabled(false);
        }else {
            binding.id.setEnabled(true);
            binding.btnNext.setText("Insert");
        }
    }
}