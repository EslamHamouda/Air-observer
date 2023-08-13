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
import androidx.navigation.fragment.navArgs
import com.example.airobserver.R
import com.example.airobserver.databinding.FragmentVerificationBinding
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
import javax.inject.Inject

@AndroidEntryPoint
class VerificationFragment : Fragment() {
    lateinit var binding: FragmentVerificationBinding
    private val viewModel: AuthViewModel by viewModels()
    //private val args:VerificationFragmentArgs by navArgs()
    @Inject
    lateinit var pref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentVerificationBinding.inflate(inflater)
        //viewModel.getOTP(args.email)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnContinue.setOnClickListener {
            viewModel.checkOTP("args.email",binding.edtCode.text.toString().toInt())
            getVerificationResponse()
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
                            findNavController().navigate(VerificationFragmentDirections.actionVerificationFragmentToLoginFragment())
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

                    /*dataResponseHandling(this@VerificationFragment.requireActivity(),
                        it,
                        binding.progressBar.progressBar,
                        {
                            viewModel.checkOTP(args.email,binding.edtCode.text.toString().toInt())
                        },
                        { it1 ->
                            try {
                                it as ApiResponseStates.Success
                                showSnackbar(it.value?.message.toString(),requireActivity())
                                findNavController().navigate(VerificationFragmentDirections.actionVerificationFragmentToLoginFragment())
                            }catch (e:Exception){
                                e.message
                            }
                        })*/
                }

        }
    }

    private fun setValidationErrors(map:MutableMap<String,Boolean>) {
        if(map["isValidOTP"]==false){
            binding.edtCode.error=getString(R.string.enter_a_valid_otp)
        }
    }

    private fun setValidationErrorsToEmpty() {
        binding.edtCode.error = null
    }

}