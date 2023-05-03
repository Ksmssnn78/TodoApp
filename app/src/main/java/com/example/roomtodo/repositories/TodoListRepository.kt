package com.example.roomtodo.repositories

import com.example.roomtodo.databases.AppDatabase
import com.example.roomtodo.models.TodoModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoListRepository @Inject constructor(private val db: AppDatabase) {
    suspend fun getAllTask(user:String): List<TodoModel> {
        return withContext(Dispatchers.IO) {
            db.todoListDao().getAllTodos(user)
        }
    }
    suspend fun insertTodo(todoModel: TodoModel) {
        return withContext(Dispatchers.IO) {
            db.todoListDao().insertTodos(todoModel)
        }
    }
    suspend fun deleteTodo(todoModel: TodoModel) {
        return withContext(Dispatchers.IO) {
            db.todoListDao().deleteTodos(todoModel)
        }
    }
    suspend fun updateTodo(todoModel: TodoModel) {
        return withContext(Dispatchers.IO) {
            db.todoListDao().updateTask(todoModel)
        }
    }
}
