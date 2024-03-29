package com.example.airobserver.presentation.auth

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.airobserver.R
import com.example.airobserver.databinding.FragmentLoginBinding
import com.example.airobserver.di.SharedPref
import com.example.airobserver.presentation.viewmodel.AuthViewModel
import com.example.airobserver.utils.ApiResponseStates
import com.example.airobserver.utils.hideProgressBar
import com.example.airobserver.utils.isValidEmail
import com.example.airobserver.utils.putData
import com.example.airobserver.utils.setValidationError
import com.example.airobserver.utils.showProgressBar
import com.example.airobserver.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    @Inject
    lateinit var pref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentLoginBinding.inflate(inflater)
        if(pref.getBoolean(SharedPref.IS_LOGIN,false))
        {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeActivity())
            requireActivity().finish()
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvRegister.setOnClickListener {
            try {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }catch (e:Exception){
                e.message
            }
        }
        binding.tvForgotpassword.setOnClickListener {
            try{
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
            }catch (e:Exception){
                e.message
            }
        }
        binding.btnLogin.setOnClickListener{
            //if(checkValidation()){
                viewModel.login(binding.edtEmail.text.toString(),binding.edtPassword.text.toString())
                getLoginResponse()
            //}
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getLoginResponse() {
        lifecycleScope.launch {
            viewModel.loginResponse.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            )
                .collectLatest {
                    when (it) {
                        is ApiResponseStates.Loading -> {
                            if(it.isLoading)
                                binding.progressBar.progressBar.showProgressBar()
                            else
                                binding.progressBar.progressBar.hideProgressBar()
                        }
                        is ApiResponseStates.Success -> {
                            setValidationErrorsToEmpty()
                            showSnackbar(it.value?.message.toString(),requireActivity())
                            pref.putData(SharedPref.IS_LOGIN,true)
                            pref.putData(SharedPref.EMAIL,it.value?.data?.email)
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeActivity())
                            requireActivity().finish()
                        }
                        is ApiResponseStates.Failure.Validation -> {
                            setValidationErrorsToEmpty()
                            setValidationErrors(it.message.toMutableMap())
                            //showSnackbar(getString(it.message.toInt()), requireActivity())
                        }
                        is ApiResponseStates.Failure.Network -> {
                            setValidationErrorsToEmpty()
                            showSnackbar(it.throwable.message.toString(), requireActivity())
                        }
                    }
                    /*dataResponseHandling(this@LoginFragment.requireActivity(),
                        it,
                        binding.progressBar.progressBar,
                        {
                            viewModel.login(binding.edtEmail.text.toString(),binding.edtPassword.text.toString())
                        },
                        { it1 ->
                            try{
                                it as ApiResponseStates.Success
                                showSnackbar(it.value?.message.toString(),requireActivity())
                                pref.putData(SharedPref.IS_LOGIN,true)
                                pref.putData(SharedPref.EMAIL,it1.email)
                                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeActivity())
                                requireActivity().finish()
                            }catch (e:Exception){
                                e.message
                            }
                        })*/
                }

        }
    }

    private fun setValidationErrors(map:MutableMap<String,Boolean>) {
        if(map["isValidEmail"] == false){
            binding.tilEmail.setValidationError(getString(R.string.enter_a_valid_email))
        }
        else if(map["isValidPassword"]==false){
            binding.tilEmail.setValidationError(getString(R.string.password_should_be_8_or_more))
        }
    }

    private fun setValidationErrorsToEmpty() {
        binding.tilEmail.error=null
        binding.tilPassword.error=null
    }

    private fun checkValidation(): Boolean {
        binding.tilEmail.error = null
        binding.tilPassword.error = null
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        if (!isValidEmail(email)) {
            binding.tilEmail.error = getString(R.string.enter_a_valid_email)
            return false
        }
        else if (password.length<8) {
            binding.tilPassword.error = getString(R.string.password_should_be_8_or_more)
            return false
        }
        return true
    }

}