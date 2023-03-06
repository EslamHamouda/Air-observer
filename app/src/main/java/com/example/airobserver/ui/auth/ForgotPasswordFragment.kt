package com.example.airobserver.ui.auth

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.airobserver.R
import com.example.airobserver.databinding.FragmentForgotPasswordBinding
import com.example.airobserver.databinding.FragmentLoginBinding
import com.example.airobserver.di.SharedPref
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.Articles
import com.example.airobserver.domain.model.response.NewsResponse
import com.example.airobserver.ui.BaseFragment
import com.example.airobserver.ui.viewmodel.AuthViewModel
import com.example.airobserver.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment() {
    lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentForgotPasswordBinding.inflate(inflater)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener {
            if(checkValidation()){
                viewModel.getOTP(binding.edtEmail.text.toString())
                getOTPResponse()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun getOTPResponse() {
        lifecycleScope.launch {
            viewModel.getOTPResponse.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            )
                .collectLatest {
                    when(it){
                        is ApiResponseStates.Success->{
                            showSnackbar("An new OTP was send.",requireActivity())
                            findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToVerificationResetPasswordFragment(binding.edtEmail.text.toString()))
                        }
                        else -> {}
                    }
                    dataResponseHandling(this@ForgotPasswordFragment.requireActivity(),
                        it,
                        binding.progressBar.progressBar,
                        {
                            viewModel.getOTP(binding.edtEmail.text.toString())
                        },
                        { it1 ->
                            it as ApiResponseStates.Success
                            showSnackbar(it.value?.message.toString(),requireActivity())
                            findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToVerificationResetPasswordFragment(binding.edtEmail.text.toString()))
                        })
                }

        }
        //findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToVerificationResetPasswordFragment(binding.edtEmail.text.toString()))
    }

    private fun checkValidation(): Boolean {
        binding.tilEmail.error = null
        val email = binding.edtEmail.text.toString()

        if (!isValidEmail(email)) {
            binding.tilEmail.error = "Enter a valid email"
            return false
        }
        return true
    }
    private fun isValidEmail(email: String): Boolean {
        val regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        return email.matches(regex.toRegex())
    }

}