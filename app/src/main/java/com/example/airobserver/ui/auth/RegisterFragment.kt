package com.example.airobserver.ui.auth

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.airobserver.R
import com.example.airobserver.databinding.ActivityOnBoardingBinding
import com.example.airobserver.databinding.FragmentRegisterBinding
import com.example.airobserver.ui.BaseFragment
import com.example.airobserver.ui.viewmodel.AuthViewModel
import com.example.airobserver.utils.ApiResponseStates
import com.example.airobserver.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        binding.autoCompleteGender.onItemClickListener=
            AdapterView.OnItemClickListener { parent, view, position, id -> showSnackbar(parent.getItemAtPosition(position).toString(),requireActivity()) }

        binding.tvOrLogin.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
        binding.btnRegister.setOnClickListener {
            getRegisterResponse()
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
                    dataResponseHandling(this@RegisterFragment.requireActivity(),
                        it,
                        binding.progressBar.progressBar,
                        {
                            viewModel.login(binding.edtEmail.text.toString(),binding.edtPassword.text.toString())
                        },
                        { it1 ->
                            showSnackbar("Registered successful",requireActivity())
                        })
                }

        }
    }


}