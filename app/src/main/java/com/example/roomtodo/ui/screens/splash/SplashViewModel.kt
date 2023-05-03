package com.example.roomtodo.ui.screens.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomtodo.databases.AppDatabase
import com.example.roomtodo.models.User
import com.example.roomtodo.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userRepository: UserRepository
) : ViewModel() {
    private val userName = "mashraf9881@gmail.com"
    private val password = "123456789"
    private val user = User(userName = userName, password = password)

    fun defaultUser(){
        viewModelScope.launch {
            userRepository.insertOneUser(user)
        }
    }
}