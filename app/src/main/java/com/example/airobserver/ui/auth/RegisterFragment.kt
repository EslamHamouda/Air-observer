package com.example.airobserver.ui.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.airobserver.R
import com.example.airobserver.databinding.ActivityOnBoardingBinding
import com.example.airobserver.databinding.FragmentRegisterBinding
import com.example.airobserver.domain.model.response.Articles
import com.example.airobserver.domain.model.response.NewsResponse
import com.example.airobserver.domain.model.response.RegisterResponse
import com.example.airobserver.ui.BaseFragment
import com.example.airobserver.ui.viewmodel.AuthViewModel
import com.example.airobserver.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {
    lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter=ArrayAdapter(requireContext(),android.R.layout.simple_dropdown_item_1line,
            arrayOf("Male","Female"))
        binding.autoCompleteGender.setAdapter(adapter)

        binding.tvOrLogin.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
        binding.btnRegister.setOnClickListener {
            //findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToVerificationFragment("eslam.ee600@gmail.com"))
            if (checkValidation()) {
                viewModel.register(binding.edtFirstname.text.toString(),
                binding.edtLastname.text.toString(),
                binding.edtEmail.text.toString(),
                binding.edtPhone.text.toString(),
                binding.edtPassword.text.toString(),
                binding.edtDate.text.toString(),
                binding.autoCompleteGender.text.toString())
                getRegisterResponse()
            }
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
                   /* when(it) {
                        is ApiResponseStates.Success -> {
                            showSnackbar("An signup was succeed and message was sent.",requireActivity())
                            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToVerificationFragment(binding.edtEmail.text.toString()))
                        }
                        else -> {}
                    }*/
                    dataResponseHandling(this@RegisterFragment.requireActivity(),
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
                            it as ApiResponseStates.Success
                            showSnackbar(it.value?.message.toString(),requireActivity())
                            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToVerificationFragment(binding.edtEmail.text.toString()))
                        })
                }

        }
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
            binding.tilFirstname.error = "Invalid first name"
            return false
        } else if (lastName.length < 3) {
            binding.tilLastname.error = "Invalid last name"
            return false
        }
        else if (!isValidEmail(email)) {
            binding.tilEmail.error = "Enter a valid email"
            return false
        }
        else if (!isValidPhoneNumber(phone)) {
            binding.tilPhone.error = "Phone not correct"
            return false
        }
        else if (!isValidDate(date)) {
            binding.tilDate.error = "Not a valid date: 2000-05-01"
            return false
        }
        else if (gender=="Gender") {
            binding.tilGender.error = "Please choose your gender"
            return false
        }
        else if (password.length<8) {
            binding.tilPassword.error = "Password should be 8 or more"
            return false
        }
        return true
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val pattern = Regex("^(01)[0-9]{9}$")
        return pattern.matches(phoneNumber)
    }

    @SuppressLint("SimpleDateFormat")
    private fun isValidDate(date: String): Boolean {
        val regex = "^\\d{4}-\\d{2}-\\d{2}$"
        if (!date.matches(regex.toRegex())) {
            return false
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        sdf.isLenient = false
        try {
            sdf.parse(date)
        } catch (e: ParseException) {
            return false
        }
        return true
    }

    private fun isValidEmail(email: String): Boolean {
        val regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        return email.matches(regex.toRegex())
    }
}