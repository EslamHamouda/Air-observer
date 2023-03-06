package com.example.airobserver.ui.auth

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.example.airobserver.R
import com.example.airobserver.databinding.FragmentRegisterBinding
import com.example.airobserver.databinding.FragmentVerificationBinding
import com.example.airobserver.databinding.FragmentVerificationResetPasswordBinding
import com.example.airobserver.ui.BaseFragment
import com.example.airobserver.ui.viewmodel.AuthViewModel
import com.example.airobserver.utils.ApiResponseStates
import com.example.airobserver.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerificationResetPasswordFragment : BaseFragment() {
    lateinit var binding: FragmentVerificationResetPasswordBinding
    private val args:VerificationFragmentArgs by navArgs()
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentVerificationResetPasswordBinding.inflate(inflater)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnContinue.setOnClickListener {
            if(binding.edtCode.text?.length==6){
                viewModel.checkOTP(args.email,binding.edtCode.text.toString().toInt())
                getVerificationResponse()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun getVerificationResponse() {
        lifecycleScope.launch {
            viewModel.checkOTPResponse.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            )
                .collectLatest {
                    when(it) {
                        is ApiResponseStates.Success -> {
                            showSnackbar("Your email is active.",requireActivity())
                            findNavController().navigate(VerificationResetPasswordFragmentDirections.actionVerificationResetPasswordFragmentToResetPasswordFragment(args.email))
                        }
                        else -> {}
                    }
                    dataResponseHandling(this@VerificationResetPasswordFragment.requireActivity(),
                        it,
                        binding.progressBar.progressBar,
                        {
                            viewModel.checkOTP(args.email,binding.edtCode.text.toString().toInt())
                        },
                        { it1 ->
                            it as ApiResponseStates.Success
                            showSnackbar(it.value?.message.toString(),requireActivity())
                            findNavController().navigate(VerificationResetPasswordFragmentDirections.actionVerificationResetPasswordFragmentToResetPasswordFragment(args.email))
                        })
                }

        }
    }

}