package com.example.androiddevelopersfirsttask.local;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.androiddevelopersfirsttask.local.dao.PostsDao;
import com.example.androiddevelopersfirsttask.local.dao.UsersDao;
import com.example.androiddevelopersfirsttask.model.entity.Post;
import com.example.androiddevelopersfirsttask.model.entity.User;

@androidx.room.Database(entities = {Post.class, User.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public static String LOGGED_IN_USER_NAME;

    public static final String DATABASE_NAME = "AppDatabase";
    private static Database INSTANCE;

    public static Database getDatabase(Context context) {
        synchronized (Database.class) {

            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, Database.class, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }

            return INSTANCE;
        }
    }

    public abstract PostsDao getPostsDao();

    public abstract UsersDao getUsersDao();
}
