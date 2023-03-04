package com.example.airobserver.ui.auth

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.airobserver.R
import com.example.airobserver.databinding.FragmentLoginBinding
import com.example.airobserver.databinding.FragmentRegisterBinding
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.request.LoginRequest
import com.example.airobserver.domain.model.response.LoginResponse
import com.example.airobserver.ui.BaseFragment
import com.example.airobserver.ui.home.news_fragment.NewsAdapter
import com.example.airobserver.ui.viewmodel.AuthViewModel
import com.example.airobserver.ui.viewmodel.NewsViewModel
import com.example.airobserver.utils.ApiResponseStates
import com.example.airobserver.utils.hideProgress
import com.example.airobserver.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        binding.tvForgotpassword.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment())
        }
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeActivity())
        }
        binding.btnLogin.setOnClickListener{
            val map= HashMap<String,String>()
            map["Email"]="adamosama908@outlook.com"
            map["Password"]="adam"
            viewModel.login(map)
            getLoginResponse()
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
                    dataResponseHandling(this@LoginFragment.requireActivity(),
                        it,
                        binding.progressBar.progressBar,
                        {
                            showSnackbar("Error",requireActivity())
                        },
                        { it1 ->
                           showSnackbar("Welcome",requireActivity())
                        })
                }

        }
    }


}