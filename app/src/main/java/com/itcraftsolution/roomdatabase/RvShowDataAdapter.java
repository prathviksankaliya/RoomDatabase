package com.itcraftsolution.roomdatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.itcraftsolution.roomdatabase.databinding.RvShowdataSampleBinding;

import java.util.ArrayList;
import java.util.List;

public class RvShowDataAdapter extends RecyclerView.Adapter<RvShowDataAdapter.viewHolder> {
    private Context context;
    private List<User> list;

    public RvShowDataAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_showdata_sample, parent, false);
        return  new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        User model = list.get(position);
        holder.binding.txFirstNameSample.setText(model.firstName);
        holder.binding.txLastNameSample.setText(model.lastName);
        holder.binding.txIdSample.setText(String.valueOf(model.getId()));

        holder.binding.igDeleteSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase database = Room.databaseBuilder(context, AppDatabase.class, "room_db").allowMainThreadQueries().build();
                UserDao userDao = database.userDao();
                // Record Deleted into Room Db
                userDao.deleteById(model.getId());
                // Record Deleted into the Recycler view
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.binding.idEditSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, MainActivity.class)
                        .putExtra("fName", model.getFirstName())
                        .putExtra("lName", model.getLastName())
                        .putExtra("id", model.getId())
                        .putExtra("update", true)
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        RvShowdataSampleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RvShowdataSampleBinding.bind(itemView);
        }
    }
}
