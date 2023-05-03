package com.example.roomtodo.ui.screens.todoList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomtodo.models.TodoModel
import com.example.roomtodo.networks.ApiExceptions
import com.example.roomtodo.repositories.TodoListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoListRepository: TodoListRepository
) : ViewModel() {

    private val _taskList: MutableLiveData<List<TodoModel>> by lazy {
        MutableLiveData<List<TodoModel>>()
    }
    val taskList: LiveData<List<TodoModel>?>
        get() = _taskList

    fun getTaskList(user: String?) = viewModelScope.launch {
        if (user != null) {
            try {
                val todoList = todoListRepository.getAllTask(user)
                _taskList.postValue(todoList)
            } catch (e: ApiExceptions) {
                _taskList.postValue(listOf())
                Timber.e("unable to get data from database")
            }
        }
    }

    fun deleteTask(task: TodoModel) = viewModelScope.launch {
        try {
            todoListRepository.deleteTodo(task)
            getTaskList(task.user)
        } catch (e: ApiExceptions) {
            Timber.e("unable to Delete User")
        }
    }

    fun updateTask(newTask: TodoModel) = viewModelScope.launch {
        try {
            todoListRepository.updateTodo(newTask)
            getTaskList(newTask.user)
        } catch (e: ApiExceptions) {
            Timber.e("unable to update data")
        }
    }

    fun insertTask(task: String?, user: String?) = viewModelScope.launch {
        if (task != null && user != null) {
            try {
                val tasks = TodoModel(user = user, task = task, check = false)
                todoListRepository.insertTodo(tasks)
                getTaskList(user)
            } catch (e: ApiExceptions) {
                Timber.e("unable to insert into database")
            }
        }
    }
}
