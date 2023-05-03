package com.example.roomtodo.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.roomtodo.models.TodoModel

@Dao
interface TodoListDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodos(todoModel: TodoModel)

    @Delete
    suspend fun deleteTodos(todoModel: TodoModel)

    @Query("SELECT * FROM todos WHERE username = :user")
    suspend fun getAllTodos(user: String): List<TodoModel>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(todoModel: TodoModel)
}
