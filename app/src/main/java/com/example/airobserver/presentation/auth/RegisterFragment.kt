package com.example.airobserver.presentation.auth

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.airobserver.R
import com.example.airobserver.databinding.FragmentRegisterBinding
import com.example.airobserver.di.SharedPref
import com.example.airobserver.presentation.viewmodel.AuthViewModel
import com.example.airobserver.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter=ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line,
            arrayOf(getString(R.string.male), getString(R.string.female)))
        binding.autoCompleteGender.setAdapter(adapter)

        binding.tvOrLogin.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
        binding.btnRegister.setOnClickListener {
            //findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToVerificationFragment("eslam.ee600@gmail.com"))
            //if (checkValidation()) {
                viewModel.register(binding.edtFirstname.text.toString(),
                binding.edtLastname.text.toString(),
                binding.edtEmail.text.toString(),
                binding.edtPhone.text.toString(),
                binding.edtPassword.text.toString(),
                binding.edtDate.text.toString(),
                binding.autoCompleteGender.text.toString())
                getRegisterResponse()
            //}
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getRegisterResponse() {
        lifecycleScope.launch {
            viewModel.registerResponse.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            )
                .collectLatest {
                    when (it) {
                        is ApiResponseStates.Loading ->{
                            if(it.isLoading)
                                binding.progressBar.progressBar.showProgressBar()
                            else
                                binding.progressBar.progressBar.hideProgressBar()
                        }
                        is ApiResponseStates.Success -> {
                            setValidationErrorsToEmpty()
                            showSnackbar(it.value?.message.toString(),requireActivity())
                            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToVerificationFragment(binding.edtEmail.text.toString()))
                        }
                        is ApiResponseStates.Failure.Validation -> {
                            setValidationErrorsToEmpty()
                            binding.progressBar.progressBar.hideProgressBar()
                            setValidationErrors(it.message.toMutableMap())
                            //showSnackbar(getString(it.message.toInt()), requireActivity())
                        }
                        is ApiResponseStates.Failure.Network -> {
                            setValidationErrorsToEmpty()
                            binding.progressBar.progressBar.hideProgressBar()
                            showSnackbar(it.throwable.message.toString(), requireActivity())
                        }
                    }

                    /*dataResponseHandling(this@RegisterFragment.requireActivity(),
                        it,
                        binding.progressBar.progressBar,
                        {
                            viewModel.register(binding.edtFirstname.text.toString(),
                                binding.edtLastname.text.toString(),
                                binding.edtEmail.text.toString(),
                                binding.edtPhone.text.toString(),
                                binding.edtPassword.text.toString(),
                                binding.edtDate.toString(),
                                binding.autoCompleteGender.text.toString())
                        },
                        { it1 ->
                            try {
                                it as ApiResponseStates.Success
                                showSnackbar(it.value?.message.toString(),requireActivity())
                                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToVerificationFragment(binding.edtEmail.text.toString()))
                            }catch (e:Exception){
                                e.message
                            }
                        })*/
                }

        }
    }

    private fun setValidationErrors(map:MutableMap<String,Boolean>) {
        if(map["isValidFname"]==false){
            binding.tilFirstname.setValidationError(getString(R.string.invalid_first_name))
        }
        else if(map["isValidLname"]==false){
            binding.tilLastname.setValidationError(getString(R.string.invalid_last_name))
        }
        else if(map["isValidEmail"]==false){
            binding.tilEmail.setValidationError(getString(R.string.enter_a_valid_email))
        }
        else if(map["isValidPhone"]==false){
            binding.tilPhone.setValidationError(getString(R.string.phone_not_correct))
        }
        else if(map["isValidBirthdate"]==false){
            binding.tilDate.setValidationError(getString(R.string.not_a_valid_date_2000_05_01))
        }
        else if(map["isValidGender"]==false){
            binding.tilGender.setValidationError(getString(R.string.please_choose_your_gender))
        }
        else if(map["isValidPassword"]==false){
            binding.tilPassword.setValidationError(getString(R.string.password_should_be_8_or_more))
        }
    }

    private fun setValidationErrorsToEmpty() {
        binding.tilFirstname.error = null
        binding.tilLastname.error = null
        binding.tilEmail.error = null
        binding.tilPhone.error = null
        binding.tilDate.error = null
        binding.tilGender.error = null
        binding.tilPassword.error = null
    }

    private fun checkValidation(): Boolean {
        binding.tilFirstname.error = null
        binding.tilLastname.error = null
        binding.tilEmail.error = null
        binding.tilPhone.error = null
        binding.tilDate.error = null
        binding.tilGender.error = null
        binding.tilPassword.error = null
        val firstName = binding.edtFirstname.text.toString()
        val lastName = binding.edtLastname.text.toString()
        val email = binding.edtEmail.text.toString()
        val phone = binding.edtPhone.text.toString()
        val date = binding.edtDate.text.toString()
        val gender = binding.autoCompleteGender.text.toString()
        val password = binding.edtPassword.text.toString()

        if (firstName.length < 3) {
            binding.tilFirstname.error = getString(R.string.invalid_first_name)
            return false
        } else if (lastName.length < 3) {
            binding.tilLastname.error = getString(R.string.invalid_last_name)
            return false
        }
        else if (!isValidEmail(email)) {
            binding.tilEmail.error = getString(R.string.enter_a_valid_email)
            return false
        }
        else if (!isValidPhoneNumber(phone)) {
            binding.tilPhone.error = getString(R.string.phone_not_correct)
            return false
        }
        else if (!isValidDate(date)) {
            binding.tilDate.error = getString(R.string.not_a_valid_date_2000_05_01)
            return false
        }
        else if (gender==getString(R.string.gender)) {
            binding.tilGender.error = getString(R.string.please_choose_your_gender)
            return false
        }
        else if (password.length<8) {
            binding.tilPassword.error = getString(R.string.password_should_be_8_or_more)
            return false
        }
        return true
    }


}