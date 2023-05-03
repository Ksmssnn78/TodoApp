package com.example.roomtodo.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomtodo.interfaces.TodoListDao
import com.example.roomtodo.interfaces.UserDao
import com.example.roomtodo.models.TodoModel
import com.example.roomtodo.models.User

@Database(
    entities = [
        User::class,
        TodoModel::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun todoListDao(): TodoListDao

    companion object {

        operator fun invoke(context: Context) = buildDatabase(context)

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "TodoApp.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}
