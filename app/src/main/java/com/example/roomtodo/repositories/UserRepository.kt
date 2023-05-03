package com.example.roomtodo.repositories

import com.example.roomtodo.databases.AppDatabase
import com.example.roomtodo.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

class UserRepository @Inject constructor(private val db:AppDatabase){

    suspend fun getAllUser():List<User>{
        return withContext(Dispatchers.IO){
            db.userDao().getAllUser()
        }
    }

    suspend fun getOneUser(userName: String, pass: String): Boolean{
        return withContext(Dispatchers.IO){
            db.userDao().getUser(userName, pass)
        }
    }

    suspend fun insertOneUser(user: User){
        return withContext(Dispatchers.IO){
            db.userDao().insertOneUser(user)
        }
    }

    suspend fun deleteUser(user :User){
        return withContext(Dispatchers.IO){
            db.userDao().deleteUser(user)
        }
    }

}