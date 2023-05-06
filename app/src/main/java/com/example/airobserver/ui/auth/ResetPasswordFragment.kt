package com.example.airobserver.ui.auth

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.airobserver.databinding.FragmentResetPasswordBinding
import com.example.airobserver.di.SharedPref
import com.example.airobserver.ui.BaseFragment
import com.example.airobserver.ui.viewmodel.AuthViewModel
import com.example.airobserver.utils.ApiResponseStates
import com.example.airobserver.utils.putData
import com.example.airobserver.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResetPasswordFragment : BaseFragment() {
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
                viewModel.newPassword(args.email,binding.edtConfirmPassword.text.toString())
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
                    when(it) {
                        is ApiResponseStates.Success -> {
                            showSnackbar("Change password is success and message was sent",requireActivity())
                            this@ResetPasswordFragment.
                            findNavController().navigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment())
                        }
                        else -> {}
                    }
                    dataResponseHandling(this@ResetPasswordFragment.requireActivity(),
                        it,
                        binding.progressBar.progressBar,
                        {
                            viewModel.newPassword(args.email,binding.edtConfirmPassword.text.toString())
                        },
                        { it1 ->
                            it as ApiResponseStates.Success
                            showSnackbar(it.value?.message.toString(),requireActivity())
                            findNavController().navigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment())
                        })
                }

        }
    }

    private fun checkValidation(): Boolean {
        binding.tilPassword.error = null
        binding.tilConfirmPassword.error = null
        val password = binding.edtPassword.text.toString()
        val confirmPassword = binding.edtConfirmPassword.text.toString()

        if (password.length<8) {
            binding.tilPassword.error = "Password should be 8 or more"
            return false
        }
        else if (confirmPassword.length<8) {
            binding.tilConfirmPassword.error = "Password should be 8 or more"
            return false
        }
        else if (!password.equals(confirmPassword)) {
            binding.tilConfirmPassword.error = "Not matched"
            return false
        }
        return true
    }

}