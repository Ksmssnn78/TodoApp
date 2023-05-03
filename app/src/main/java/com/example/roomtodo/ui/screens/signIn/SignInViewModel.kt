package com.example.roomtodo.ui.screens.signIn

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomtodo.models.User
import com.example.roomtodo.networks.ApiExceptions
import com.example.roomtodo.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isValid: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isValid: LiveData<Boolean>
        get() = _isValid

    fun authSignIn(uname: String?, pass: String?) {
        var flag: Boolean
        viewModelScope.launch {
            try {
                flag = if (uname != null && pass != null) {
                    userRepository.getOneUser(uname, pass)
                } else {
                    false
                }
                _isValid.postValue(flag)
            } catch (e: ApiExceptions) {
                _isValid.postValue(false)
                Timber.e("Login failed")
            }
        }
    }

    fun insertUser(user: String?, pass: String?) = viewModelScope.launch {
        if (user != null && pass != null) {
            try {
                val newUser = User(userName = user, password = pass)
                userRepository.insertOneUser(newUser)
            } catch (e: ApiExceptions) {
                Timber.e("unable to insert new user")
            }
        }
    }
}
