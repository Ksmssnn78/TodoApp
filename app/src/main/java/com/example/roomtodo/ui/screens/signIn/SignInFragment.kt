package com.example.roomtodo.ui.screens.signIn

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.roomtodo.R
import com.example.roomtodo.Utils.Extensions.SharedPref
import com.example.roomtodo.databinding.FragmentSignInBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private lateinit var signInBinding: FragmentSignInBinding

    private val viewModel: SignInViewModel by viewModels()

    private lateinit var userName: String
    private var gFlag = false

    @Inject
    lateinit var sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(context, "back button pressed", Toast.LENGTH_SHORT).show()
        }
        callback.isEnabled
        initObserve()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        signInBinding = FragmentSignInBinding.bind(view)

        signInBinding.loginBtnLogin.setOnClickListener {
            userName = signInBinding.emailedittxtLogin.text.toString()
            val pass = signInBinding.passEditTxtLogin.text.toString()
            checkCredential(userName, pass)
        }

        signInBinding.addUserFabBtnTodoList.setOnClickListener {
            addNewUser()
        }
    }

    //    @SuppressLint("ResourceAsColor")
    private fun addNewUser() {
        val linLayout = LinearLayout(requireContext())
        linLayout.orientation = LinearLayout.VERTICAL
        linLayout.setPadding(5)
        val usernameContainer = TextInputLayout(
            requireContext(),
            null,
            com.google.android.material.R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox
        )
        usernameContainer.setPadding(10)
        val username = TextInputEditText(requireContext())
        username.hint = "Email"
        username.textSize = 17.0F
        username.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
//        username.setTextColor(R.color.sky_blue)
        usernameContainer.addView(username)
        val passwordContainer = TextInputLayout(
            requireContext(),
            null,
            com.google.android.material.R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox
        )
        passwordContainer.setPadding(10)
        val password = TextInputEditText(requireContext())
        password.hint = "Password"
        password.textSize = 17.0F
        password.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
//        password.setTextColor(R.color.sky_blue)
        passwordContainer.addView(password)
        linLayout.addView(usernameContainer, 0)
        linLayout.addView(passwordContainer, 1)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Welcome to TODO App...")
            .setView(linLayout)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Confirm") { _, _ ->
                viewModel.insertUser(username.text.toString(), password.text.toString())
            }.show()
    }

    private fun checkCredential(user: String, pass: String) {
        viewModel.authSignIn(user, pass)
    }

    private fun initObserve() {
        viewModel.isValid.observe(this) {
            if (it) {
                sharedPref.saveUser(userName)
                findNavController().navigate(R.id.action_signInFragment_to_todoListFragment)
            } else {
                if (gFlag) {
                    Snackbar.make(
                        signInBinding.loginBtnLogin,
                        "Empty or Wrong Credential!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                gFlag = true
            }
        }
    }
}
