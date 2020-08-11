package com.example.androiddevelopersfirsttask.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.androiddevelopersfirsttask.model.entity.User;

@Dao
public interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("select password from users where email = :email")
    String getPassword(String email);

    @Query("select email from users where email = :email")
    String getUserName(String email);

    @Query("select name from users where email = :email")
    String getName(String email);

}
