package com.example.roomtodo.ui.screens.splash

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.roomtodo.R
import com.example.roomtodo.Utils.Extensions.SharedPref
import com.example.roomtodo.databases.AppDatabase
import com.example.roomtodo.databinding.FragmentSplashBinding
import com.example.roomtodo.networks.ApiExceptions
import dagger.hilt.android.AndroidEntryPoint
import hilt_aggregated_deps._dagger_hilt_android_internal_modules_ApplicationContextModule
import javax.inject.Inject
import timber.log.Timber

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {
    private lateinit var splashBinding: FragmentSplashBinding

    private val viewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var sharedPref: SharedPref



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        splashBinding = FragmentSplashBinding.bind(view)

        viewModel.defaultUser()
        splashScreen()

    }

    private fun splashScreen() {
        LongOps().execute()
    }

    private open inner class LongOps : AsyncTask<String?, Void?, String?>() {
        override fun doInBackground(vararg p0: String?): String? {
            try {
                Thread.sleep(2000)
            } catch (e: ApiExceptions) {
                Thread.interrupted()
                Timber.e(e)
            }
            return "Result"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            if (sharedPref.getUser() == null) {
                val action = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
                findNavController().navigate(action)
            }else{
                val action = SplashFragmentDirections.actionSplashFragmentToTodoListFragment()
                findNavController().navigate(action)
            }
        }
    }
}
