package com.itcraftsolution.roomdatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertAll(User users);

    @Query("select * from user")
    List<User> getAllRecords();

    @Query("delete from user where id = :id")
    void deleteById(int id);

    @Query("update user set firstName = :fName, lastName = :lName where id = :id")
    void updateById(int id , String fName, String lName);
}
