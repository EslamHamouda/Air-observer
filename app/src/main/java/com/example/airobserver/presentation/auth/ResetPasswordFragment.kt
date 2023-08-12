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
import androidx.navigation.fragment.navArgs
import com.example.airobserver.R
import com.example.airobserver.databinding.FragmentResetPasswordBinding
import com.example.airobserver.presentation.viewmodel.AuthViewModel
import com.example.airobserver.utils.ApiResponseStates
import com.example.airobserver.utils.dataResponseHandling
import com.example.airobserver.utils.hideProgressBar
import com.example.airobserver.utils.setValidationError
import com.example.airobserver.utils.showProgressBar
import com.example.airobserver.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {
    lateinit var binding: FragmentResetPasswordBinding
    private val viewModel: AuthViewModel by viewModels()
    private val args:VerificationFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentResetPasswordBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnConfirm.setOnClickListener {
            if (checkValidation()){
                viewModel.newPassword(args.email, binding.edtPassword.text.toString(), binding.edtConfirmPassword.text.toString())
                getResetPasswordResponse()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getResetPasswordResponse() {
        lifecycleScope.launch {
            viewModel.newPasswordResponse.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            )
                .collectLatest {
                    when (it) {
                        is ApiResponseStates.Loading -> binding.progressBar.progressBar.showProgressBar()
                        is ApiResponseStates.Success -> {
                            setValidationErrorsToEmpty()
                            binding.progressBar.progressBar.hideProgressBar()
                            showSnackbar(it.value?.message.toString(),requireActivity())
                            findNavController().navigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment())
                        }
                        is ApiResponseStates.ValidationFailure -> {
                            setValidationErrorsToEmpty()
                            binding.progressBar.progressBar.hideProgressBar()
                            setValidationErrors(it.message.toMutableMap())
                            //showSnackbar(getString(it.message.toInt()), requireActivity())
                        }
                        is ApiResponseStates.Failure -> {
                            setValidationErrorsToEmpty()
                            binding.progressBar.progressBar.hideProgressBar()
                            showSnackbar(it.throwable.message.toString(), requireActivity())
                        }
                    }
                    /*dataResponseHandling(this@ResetPasswordFragment.requireActivity(),
                        it,
                        binding.progressBar.progressBar,
                        {
                            viewModel.newPassword(args.email,binding.edtConfirmPassword.text.toString())
                        },
                        { it1 ->
                            try {
                                it as ApiResponseStates.Success
                                showSnackbar(it.value?.message.toString(),requireActivity())
                                findNavController().navigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment())
                            }catch (e:Exception){
                                e.message
                            }
                        })*/
                }

        }
    }

    private fun setValidationErrors(map:MutableMap<String,String>) {
        map["isValidPassword"]?.let { getString(it.toInt()) }
            ?.let { binding.tilPassword.setValidationError(it) }
        map["isConfirmValidPassword"]?.let { getString(it.toInt()) }
            ?.let { binding.tilConfirmPassword.setValidationError(it) }
        map["isMatch"]?.let { getString(it.toInt()) }
            ?.let { binding.tilConfirmPassword.setValidationError(it) }
    }

    private fun setValidationErrorsToEmpty() {
        binding.tilPassword.error = null
        binding.tilConfirmPassword.error = null
    }

    private fun checkValidation(): Boolean {
        binding.tilPassword.error = null
        binding.tilConfirmPassword.error = null
        val password = binding.edtPassword.text.toString()
        val confirmPassword = binding.edtConfirmPassword.text.toString()

        if (password.length<8) {
            binding.tilPassword.error = getString(R.string.password_should_be_8_or_more)
            return false
        }
        else if (confirmPassword.length<8) {
            binding.tilConfirmPassword.error = getString(R.string.password_should_be_8_or_more)
            return false
        }
        else if (password != confirmPassword) {
            binding.tilConfirmPassword.error = getString(R.string.not_matched)
            return false
        }
        return true
    }

}