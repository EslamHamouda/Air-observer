package com.example.airobserver.presentation.auth

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
import com.example.airobserver.databinding.FragmentForgotPasswordBinding
import com.example.airobserver.di.SharedPref
import com.example.airobserver.presentation.viewmodel.AuthViewModel
import com.example.airobserver.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {
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
            //if(checkValidation()){
                viewModel.getOTP(binding.edtEmail.text.toString())
                getOTPResponse()
            //}
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
                    when (it) {
                        is ApiResponseStates.Loading -> binding.progressBar.progressBar.showProgressBar()
                        is ApiResponseStates.Success -> {
                            binding.progressBar.progressBar.hideProgressBar()
                            showSnackbar(it.value?.message.toString(),requireActivity())
                            findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToVerificationResetPasswordFragment(binding.edtEmail.text.toString()))
                        }
                        is ApiResponseStates.ValidationFailure -> {
                            binding.progressBar.progressBar.hideProgressBar()
                            showSnackbar(getString(it.message.toInt()), requireActivity())
                        }
                        is ApiResponseStates.Failure -> {
                            binding.progressBar.progressBar.hideProgressBar()
                            showSnackbar(it.throwable.message.toString(), requireActivity())
                        }
                    }
                    /*dataResponseHandling(this@ForgotPasswordFragment.requireActivity(),
                        it,
                        binding.progressBar.progressBar,
                        {
                            viewModel.getOTP(binding.edtEmail.text.toString())
                        },
                        { it1 ->
                            try {
                                it as ApiResponseStates.Success
                                showSnackbar(it.value?.message.toString(),requireActivity())
                                findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToVerificationResetPasswordFragment(binding.edtEmail.text.toString()))
                            }catch (e:Exception){
                               e.message
                            }
                        })*/
                }

        }
    }

    private fun checkValidation(): Boolean {
        binding.tilEmail.error = null
        val email = binding.edtEmail.text.toString()

        if (!isValidEmail(email)) {
            binding.tilEmail.error = getString(R.string.enter_a_valid_email)
            return false
        }
        return true
    }

}